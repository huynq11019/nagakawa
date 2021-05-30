package com.nagakawa.guarantee.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import com.nagakawa.guarantee.security.jwt.JWTConfigurer;
import com.nagakawa.guarantee.security.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenUtil;

    private final CorsFilter corsFilter;
    
    private final SecurityProblemSupport problemSupport;
	
	private static final String[] IGNOR_URLS = {
			"/app/**/*.{js,html}",
            "/i18n/**",
            "/content/**",
            "/swagger-ui/index.html",
            "/test/**"
	};
	
	private static final String[] PUBLIC_URLS = {
			"/api/authenticate",
			"/api/authenticate/**",
			"/api/oauth2/ybi/callback",
			"/api/register",
			"/api/users",
			"/api/activate",
			"/api/account/reset-password/init",
			"/api/account/reset-password/finish",
			"/api/file/**",
			"/api/public/**",
			"/api/payment/**",
			"/fake_api/**",
			"/api/nodes/findAll",
			"/api/health-facilities/*"
	};
	
	private static final String[] PUBLIC_POST_URLS = {
			"/api/doctor-appointments",
			"/api/transactions"
	};
	
	private static final String[] PUBLIC_GET_URLS = {
			"/api/health-facilities/{id}",
			"/api/health-facilities/*"
	};
	
	private static final String[] AUTHENTICATED_URLS = {
			"/api/**"
	};
	


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(IGNOR_URLS);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
                .csrf()
                .disable()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
                .and()
                .headers()
                .contentSecurityPolicy("default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'; style-src 'self' 'unsafe-inline'; img-src 'self' data:")
                .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                .and()
                .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
                .and()
                .frameOptions()
                .deny()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(PUBLIC_URLS).permitAll()
                .antMatchers(HttpMethod.POST, PUBLIC_POST_URLS).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_GET_URLS).permitAll()
                .antMatchers(AUTHENTICATED_URLS).authenticated()
                .and()
                .httpBasic()
                .and()
                .apply(securityConfigurerAdapter());
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(jwtTokenUtil);
    }
}
