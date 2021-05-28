package com.nagakawa.guarantee.configuration;

import java.util.Collections;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.nagakawa.guarantee.cache.CachingHttpHeadersFilter;

/**
 * Configuration of web application with Servlet 3.0 APIs.
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer {

    private final Logger log = LoggerFactory.getLogger(WebConfigurer.class);

    private final Environment env;

    public WebConfigurer(Environment env) {
        this.env = env;
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.getActiveProfiles().length != 0) {
            log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
        }
        
        EnumSet<DispatcherType> disps = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD,
                DispatcherType.ASYNC);
        
        if (env.acceptsProfiles(Profiles.of(EnvConstants.SPRING_PROFILE_PRODUCTION))) {
            initCachingHttpHeadersFilter(servletContext, disps);
        }
        
        log.info("Web application fully configured");
    }

    /**
     * Initializes the caching HTTP Headers Filter.
     */
	private void initCachingHttpHeadersFilter(ServletContext servletContext, EnumSet<DispatcherType> disps) {
		log.info("Registering Caching HTTP Headers Filter");
		FilterRegistration.Dynamic cachingHttpHeadersFilter = servletContext.addFilter("cachingHttpHeadersFilter",
				new CachingHttpHeadersFilter());

		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/i18n/*");
		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/content/*");
		cachingHttpHeadersFilter.addMappingForUrlPatterns(disps, true, "/app/*");

		cachingHttpHeadersFilter.setAsyncSupported(true);
	}

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.addAllowedOrigin("*");
        config.setExposedHeaders(Collections.singletonList("Authorization,Link,X-Total-Count,X-Total-Active-Count,X-Action-Allow,X-Action-Mesage"));
        config.setAllowCredentials(true);
        
        if (config.getAllowedOrigins() != null && !config.getAllowedOrigins().isEmpty()) {
            log.info("Registering CORS filter");
            source.registerCorsConfiguration("/api/**", config);
            source.registerCorsConfiguration("/v2/api-docs", config);
        }
        
        return new CorsFilter(source);
    }

//    @Override
//    public void customize(WebServerFactory server) {
//        setMimeMappings(server);
//        setLocationForStaticAssets(server);
//    }
//
//    private void setMimeMappings(WebServerFactory server) {
//        if (server instanceof ConfigurableServletWebServerFactory) {
//            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
//            mappings.add("html", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
//            mappings.add("json", MediaType.TEXT_HTML_VALUE + ";charset=" + StandardCharsets.UTF_8.name().toLowerCase());
//            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
//            servletWebServer.setMimeMappings(mappings);
//        }
//    }
//
//    private void setLocationForStaticAssets(WebServerFactory server) {
//        if (server instanceof ConfigurableServletWebServerFactory) {
//            ConfigurableServletWebServerFactory servletWebServer = (ConfigurableServletWebServerFactory) server;
//            File root;
//            String prefixPath = resolvePathPrefix();
//            root = new File(prefixPath + "target/classes/static/");
//            if (root.exists() && root.isDirectory()) {
//                servletWebServer.setDocumentRoot(root);
//            }
//        }
//    }
//
//    private String resolvePathPrefix() {
//        String fullExecutablePath;
//        try {
//            fullExecutablePath = decode(this.getClass().getResource("").getPath(), StandardCharsets.UTF_8.name());
//        } catch (UnsupportedEncodingException e) {
//            fullExecutablePath = this.getClass().getResource("").getPath();
//        }
//        String rootPath = Paths.get(".").toUri().normalize().getPath();
//        String extractedPath = fullExecutablePath.replace(rootPath, "");
//        int extractionEndIndex = extractedPath.indexOf("target/");
//        if (extractionEndIndex <= 0) {
//            return "";
//        }
//        return extractedPath.substring(0, extractionEndIndex);
//    }
//
//
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/*.js", "/*.css", "/*.ttf", "/*.woff", "/*.woff2", "/*.eot",
//                "/*.svg")
//                .addResourceLocations("classpath:/static/")
//                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS)
//                        .cachePrivate()
//                        .mustRevalidate())
//                .resourceChain(true)
//                .addResolver(new PathResourceResolver());
//    }
}
