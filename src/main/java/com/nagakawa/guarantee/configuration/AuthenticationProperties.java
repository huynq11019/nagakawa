package com.nagakawa.guarantee.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@Getter
public class AuthenticationProperties {
    @Value("${security.authentication.jwt.base64-secret}")
	private String base64Secret;
	
    @Value("${security.authentication.jwt.token-duration}")
    private int tokenDuration;
    
    @Value("${security.authentication.jwt.token-remember-me-duration}")
    private int tokenRememberMeDuration;
    
    @Value("${security.authentication.jwt.refesh-token-duration}")
    private int refeshTokenDuration;
    
    @Value("${security.authentication.jwt.data-duration}")
    private int dataDuration;
    
    @Value("${security.cache.url-patterns}")
    private String[] urlPatterns;
    
    @Value("${security.cors.allowed-origins}")
    private String allowedOrigins;
    
    @Value("${security.cors.allowed-methods}")
    private String allowedMethods;
    
    @Value("${security.cors.allowed-headers}")
    private String allowedHeaders;
    
    @Value("${security.cors.exposed-headers}")
    private String exposedHeaders;
    
    @Value("${security.cors.allow-credentials}")
    private boolean allowCredentials;
    
    @Value("${security.cors.max-age}")
    private long maxAge;
}
