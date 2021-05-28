package com.nagakawa.guarantee.service.util;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagakawa.guarantee.api.handler.RestTemplateResponseErrorHandler;
import com.nagakawa.guarantee.security.util.SecurityUtils;

@Component
public class RestTemplateHelper {

    private static final Logger log = LoggerFactory.getLogger(RestTemplateHelper.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public RestTemplateHelper(RestTemplateResponseErrorHandler errorHandler, RestTemplateBuilder builder) {
        this.restTemplate = builder.errorHandler(errorHandler).build();
        this.objectMapper = new ObjectMapper();
    }

    public <T> T execute(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> clazz, String... params) {
        ResponseEntity<String> response = restTemplate.exchange(url, method, new HttpEntity(body, headers), String.class, params);
        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                if (response.hasBody())
                    return objectMapper.readValue(response.getBody(), clazz);
                else
                    return null;
            }
        } catch (Exception e) {
            log.error("Error: ", e);
        }
        return null;
    }

    public <T> T execute(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> clazz) {
        return this.execute(url, method, headers, body, clazz, "");
    }

    public <T> T execute(String url, HttpMethod method, Object body, Class<T> clazz, String... params) {
        return this.execute(url, method, this.generateDefaultHeaders(), body, clazz, params);
    }

    public <T> T execute(String url, HttpMethod method, Object body, Class<T> clazz) {
        return this.execute(url, method, this.generateDefaultHeaders(), body, clazz);
    }

    public <T> T executeWithPredefinedHeader(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> clazz) {
        ResponseEntity<String> response = restTemplate.exchange(url, method, new HttpEntity(body, headers), String.class);
        try {
            if (response.getStatusCode().is2xxSuccessful()) {
                if (response.hasBody())
                    return objectMapper.readValue(response.getBody(), clazz);
                else
                    return null;
            }
        } catch (JsonProcessingException e) {
            log.error("Error: ", e);
        } catch (RestClientException e) {
            log.error("Error: ", e);
        }
        return null;
    }

    private HttpHeaders generateDefaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        
        headers.setBearerAuth(SecurityUtils.getCurrentUserJWT().orElse(null));
        
        return headers;
    }
}
