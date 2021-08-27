package com.nagakawa.guarantee;

import java.util.Arrays;
import java.util.EnumSet;
import javax.annotation.PostConstruct;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.nagakawa.guarantee.cache.CachingHttpHeadersFilter;
import com.nagakawa.guarantee.configuration.EnvConstants;
import com.nagakawa.guarantee.security.configuration.AuthenticationProperties;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ServletInitializer extends SpringBootServletInitializer implements ServletContextInitializer {	
	private final AuthenticationProperties ap;
    
    private final Environment env;
	
    private final RedisTemplate<String, Object> redisTemplate;
    
    @PostConstruct
    public void connection() {
        try {
        	redisTemplate.getConnectionFactory().getConnection();
        } catch (Exception e) {
            System.out.println("-------------------------------------------------------------------------------------------");
            System.out.println("- Redis host and port is not availables. please check application configuration file. -");
            System.out.println("-------------------------------------------------------------------------------------------");
            
            System.exit(-1);
        }
    }
    
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NagakawaGuaranteeApplication.class);
	}

	@Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            _log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
                DispatcherType.ASYNC);
        
        if (env.acceptsProfiles(Profiles.of(EnvConstants.Profile.PRODUCTION))) {
            initCachingHttpHeadersFilter(servletContext, disps);
        }
        
        _log.info("Web application fully configured");
    }
    /**
     * Initializes the caching HTTP Headers Filter.
     */
	private void initCachingHttpHeadersFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
		_log.info("Registering Caching HTTP Headers Filter");
		
		FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext.addFilter("cachingHttpHeadersFilter",
				new CachingHttpHeadersFilter());

		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, ap.getUrlPatterns());

		cachingHttpHeadersFilter.setAsyncSupported(true);
	}
	
	@Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedHeaders(
				Arrays.asList(StringUtil.split(ap.getAllowedHeaders(), StringPool.COMMA)));
		config.setAllowedMethods(
				Arrays.asList(StringUtil.split(ap.getAllowedMethods(), StringPool.COMMA)));
		config.setAllowedOrigins(
				Arrays.asList(StringUtil.split(ap.getAllowedOrigins(), StringPool.COMMA)));
		config.setExposedHeaders(
				Arrays.asList(StringUtil.split(ap.getExposedHeaders(), StringPool.COMMA)));
		config.setAllowCredentials(ap.isAllowCredentials());
		config.setMaxAge(ap.getMaxAge());
        
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            _log.info("Registering CORS filter");
            
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        
        return new CorsFilter(source);
    }
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")//
						.exposedHeaders(StringUtil.split(ap.getExposedHeaders(), StringPool.COMMA))
						.allowedOrigins(StringUtil.split(ap.getAllowedOrigins(), StringPool.COMMA))//
						.allowedHeaders(StringUtil.split(ap.getAllowedHeaders(), StringPool.COMMA))
						.allowedMethods("GET", "POST", "OPTIONS")//
						.maxAge(ap.getMaxAge());
				;
			}
		};
	}
}
