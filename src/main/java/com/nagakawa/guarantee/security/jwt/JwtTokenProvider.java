package com.nagakawa.guarantee.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.nagakawa.guarantee.configuration.AuthenticationProperties;
import com.nagakawa.guarantee.model.AccessToken;
import com.nagakawa.guarantee.redis.service.RedisService;
import com.nagakawa.guarantee.repository.AccessTokenRepository;
import com.nagakawa.guarantee.security.UserPrincipal;
import com.nagakawa.guarantee.security.exception.InvalidTokenRequestException;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.security.util.SecurityUtils;
import com.nagakawa.guarantee.util.DateUtils;
import com.nagakawa.guarantee.util.HMACUtil;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.Validator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private static final Logger _log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private Key key;

    private final AuthenticationProperties properties;

    private final UserDetailsService userDetailsService;

    private final RedisService redisService;

    private final AccessTokenRepository accessTokenRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes;

        String secret = properties.getBase64Secret();

        if (StringUtils.isEmpty(secret)) {
            _log.warn("Warning: the JWT key used is not Base64-encoded. "
                    + "We recommend using the `spring.security.authentication.jwt.base64-secret` key for optimum security.");

            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            _log.info("Using a Base64-encoded JWT secret key");

            keyBytes = Decoders.BASE64.decode(secret);
        }

        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // access token

    public String createAccessToken(String username) {
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        
        String hashKey = getHashKey(username);
        
        ((UserPrincipal) principal).setHashKey(hashKey);
        
        redisService.hset(getRedisKey(username, hashKey), SecurityConstants.Jwt.USER_DETAIL, principal);
        
        Date validity = DateUtils.getDateAfter(new Date(), properties.getTokenDuration());
        
        String authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        
        String jwt = Jwts.builder()
                .setSubject(username)
                .claim(SecurityConstants.Jwt.PRIVILEGES, authorities)
                .claim(SecurityConstants.Jwt.HASHKEY, hashKey)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(validity).compact();

        // save token to db
        AccessToken accessToken = AccessToken.builder()
                .token(jwt)
                .expiredDate(validity.toInstant())
                .expired(false)
                .build();
        
        accessTokenRepository.save(accessToken);

        return jwt;
    }
    
    public String createAccessToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String username = authentication.getName();

        Object ob = authentication.getPrincipal();

        UserDetails principal = ob instanceof UserPrincipal ? (UserPrincipal) ob
                : userDetailsService.loadUserByUsername(username);

        String hashKey = getHashKey(username);

        ((UserPrincipal) principal).setHashKey(hashKey);
        // store UserDetails in redis
        redisService.hset(getRedisKey(username, hashKey), SecurityConstants.Jwt.USER_DETAIL, principal);

        Date validity = DateUtils.getDateAfter(new Date(),
                (rememberMe ? properties.getTokenDuration() : properties.getTokenRememberMeDuration()));

        String jwt = Jwts.builder()
                .setSubject(username)
                .claim(SecurityConstants.Jwt.PRIVILEGES, authorities)
                .claim(SecurityConstants.Jwt.HASHKEY, hashKey)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(validity).compact();

        // save token to db
        AccessToken accessToken = AccessToken.builder()
                .token(jwt)
                .expiredDate(validity.toInstant())
                .expired(false)
                .build();
        
        accessTokenRepository.save(accessToken);

        return jwt;
    }

    /*
     * Create refresh token
     */
    
    public String createRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        
        String refreshToken = HMACUtil.hashSha256(UUID.randomUUID().toString() + username);
        
        //store in resdis
        String hashKey = getHashKey(username);
        
        redisService.hset(getRedisKey(username, hashKey), SecurityConstants.Jwt.REFRESH_TOKEN, refreshToken,
                properties.getRefeshTokenDuration(), TimeUnit.DAYS);
        
        return refreshToken;
    }
    
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get(SecurityConstants.Jwt.PRIVILEGES).toString().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        String username = claims.getSubject();

        String hashKey = claims.get(SecurityConstants.Jwt.HASHKEY).toString();

        // retrieve UserDetails from redis
        Object ob = redisService.hget(getRedisKey(username, hashKey), SecurityConstants.Jwt.USER_DETAIL);

        UserDetails principal = null;

        if (ob instanceof UserPrincipal) {
            principal = (UserPrincipal) ob;

            ((UserPrincipal) principal).setHashKey(hashKey);
        }

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        String username = StringPool.BLANK;

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken).getBody();

            username = claims.getSubject();
        } catch (MalformedJwtException ex) {
            _log.error("Invalid JWT token");

            throw new InvalidTokenRequestException("JWT", authToken, "Malformed jwt token");

        } catch (ExpiredJwtException ex) {
            _log.error("Expired JWT token");

            throw new InvalidTokenRequestException("JWT", authToken, "Token expired. Refresh required");

        } catch (UnsupportedJwtException ex) {
            _log.error("Unsupported JWT token");
            throw new InvalidTokenRequestException("JWT", authToken, "Unsupported JWT token");

        } catch (IllegalArgumentException ex) {
            _log.error("JWT claims string is empty.");

            throw new InvalidTokenRequestException("JWT", authToken, "Illegal argument token");
        } catch (Exception e) {
            _log.error("Invalid JWT signature.", e);
        }

        return !isTokenInBlackList(username, authToken);
    }

    public void invalidateToken() {
        Optional<UserDetails> userDetails = SecurityUtils.geUserDetails();

        if (userDetails.isPresent() && (userDetails.get() instanceof UserPrincipal)) {
            UserPrincipal userPrincipal = (UserPrincipal) userDetails.get();

            String username = userPrincipal.getUsername();

            String hashKey = userPrincipal.getHashKey();

            // remove data from redis
            redisService.hdelete(getRedisKey(username, hashKey));

            // invalidate all token belong to username in db
            int result = accessTokenRepository.expiredTokenByUsername(username);

            if (_log.isDebugEnabled()) {
                _log.debug("{} token(s) expired", result);
            }
        }
    }

    public Optional<String> refreshToken(String username, String refreshToken) {
        String hashKey = getHashKey(username);

        return Optional
                .ofNullable(redisService.hget(getRedisKey(username, hashKey), SecurityConstants.Jwt.REFRESH_TOKEN))
                .map(ob -> {
                    return createAccessToken(username);
                });
    }
    
    private boolean isTokenInBlackList(String username, String token) {
        Optional<AccessToken> accessToken = accessTokenRepository.findById(token);

        if (Validator.isNull(username) || !accessToken.isPresent()) {
            return true;
        }

        AccessToken aToken = accessToken.get();

        return !username.equalsIgnoreCase(aToken.getCreatedBy())
                || aToken.getExpiredDate().isBefore(Instant.now())
                || aToken.isExpired();
    }

    private String getHashKey(String username) {
        return Validator.isNotNull(username) ? DigestUtils.md5DigestAsHex(username.getBytes()) : StringPool.BLANK;
    }

    private String getRedisKey(String username, String hashKey) {
        return String.format("%s:%s", username, hashKey);
    }
}
