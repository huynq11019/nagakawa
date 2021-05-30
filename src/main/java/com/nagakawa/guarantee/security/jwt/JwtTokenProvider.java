package com.nagakawa.guarantee.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
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

import com.nagakawa.guarantee.configuration.AuthenticationProperties;
import com.nagakawa.guarantee.redis.service.RedisService;
import com.nagakawa.guarantee.security.UserPrincipal;
import com.nagakawa.guarantee.security.util.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements InitializingBean {

    private static final Logger _log = LoggerFactory.getLogger(JwtTokenProvider.class);

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final AuthenticationProperties properties;

    private final UserDetailsService userDetailsService;
    
    private final RedisService redisService;

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes;
        
        String secret = properties.getBase64Secret();
        
        if (StringUtils.isEmpty(secret)) {
            _log.warn("Warning: the JWT key used is not Base64-encoded. " +
                    "We recommend using the `spring.security.authentication.jwt.base64-secret` key for optimum security.");
            
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        } else {
            _log.info("Using a Base64-encoded JWT secret key");
            
            keyBytes = Decoders.BASE64.decode(secret);
        }
        
        this.key = Keys.hmacShaKeyFor(keyBytes);
        
        this.tokenValidityInMilliseconds =
                1000 * properties.getTokenValidityInSeconds();
        
        this.tokenValidityInMillisecondsForRememberMe =
                1000 * properties.getTokenValidityInSecondsForRememberMe();
    }

    //refresh token
    
    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String username = authentication.getName();
        
        Object ob = authentication.getPrincipal();
        
		UserDetails principal = ob instanceof UserPrincipal ? (UserPrincipal) ob
				: userDetailsService.loadUserByUsername(username);
        
		String hashKey = getHashKey(username);
        
		//store UserDetails in redis
		redisService.saveObjectToRedis(String.format("%s:%s", username, hashKey), principal);
		
        long now = (new Date()).getTime();
        
		Date validity = new Date(
				now + (rememberMe ? this.tokenValidityInMillisecondsForRememberMe : this.tokenValidityInMilliseconds));

        String jwt = Jwts.builder()
                .setSubject(username)
                .claim(SecurityConstants.Jwt.PRIVILEGES, authorities)
                .claim(SecurityConstants.Jwt.HASHKEY, hashKey)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .compact();
        
        return jwt;
    }

	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(SecurityConstants.Jwt.PRIVILEGES).toString().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		String username = claims.getSubject();

		String hashKey = claims.get(SecurityConstants.Jwt.HASHKEY).toString();

		//retrieve UserDetails from redis
		Object ob = redisService.getObjectFromRedis(String.format("%s:%s", username, hashKey));

		UserDetails principal = ob instanceof UserPrincipal ? (UserPrincipal) ob : null;

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

    public boolean validateToken(String authToken) {
        try {
            Jwts.parserBuilder()
            	.setSigningKey(key)
            	.build()
            	.parseClaimsJws(authToken);
            
            return true;
        } catch (Exception e) {
            _log.error("Invalid JWT signature.", e);
        }
        
        return false;
    }
    
    private String getHashKey(String username) {
    	return DigestUtils.md5DigestAsHex(username.getBytes());
    }
}
