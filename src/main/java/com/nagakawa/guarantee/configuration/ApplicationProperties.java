package com.nagakawa.guarantee.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * @author LinhLH
 */
@Configuration
@Getter
public class ApplicationProperties {
	@Value("${application.folderUpload}")
    private String folderUpload;
    
	@Value("${application.template}")
    private String template;
}
