package com.nagakawa.guarantee.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Configuration
@ConfigurationProperties(prefix = "security.authentication.jwt")
@Getter
public class AuthenticationProperties {
    private String base64Secret;
    private long tokenValidityInSeconds = 10000;
    private long tokenValidityInSecondsForRememberMe = 100000;
}
