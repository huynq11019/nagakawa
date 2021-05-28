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
import org.springframework.util.StringUtils;

import com.nagakawa.guarantee.configuration.AuthenticationProperties;
import com.nagakawa.guarantee.security.util.SecurityConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil implements InitializingBean {

    private static final Logger _log = LoggerFactory.getLogger(JwtTokenUtil.class);

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final AuthenticationProperties properties;

    private final UserDetailsService userDetailsService;

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
            
            keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        }
        
        this.key = Keys.hmacShaKeyFor(keyBytes);
        
        this.tokenValidityInMilliseconds =
                1000 * properties.getTokenValidityInSeconds();
        
        this.tokenValidityInMillisecondsForRememberMe =
                1000 * properties.getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        
        Date validity;
        
        if (rememberMe) {
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        String jwt = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(SecurityConstants.JwtClaimKey.PRIVILEGES, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
        
        return jwt;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(SecurityConstants.JwtClaimKey.PRIVILEGES).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = userDetailsService.loadUserByUsername(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            
            return true;
        } catch (Exception e) {
            _log.error("Invalid JWT signature.", e);
        }
        
        return false;
    }
}
