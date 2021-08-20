package com.nagakawa.guarantee.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JWTTokenProvider jwtTokenProvider;

    public JWTConfigurer(JWTTokenProvider tokenProvider) {
        this.jwtTokenProvider = tokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
    	JWTCookieFilter customFilter = new JWTCookieFilter(jwtTokenProvider);
        
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
