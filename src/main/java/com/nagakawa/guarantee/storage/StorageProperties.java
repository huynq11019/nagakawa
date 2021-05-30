package com.nagakawa.guarantee.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * @author LinhLH
 */
@Configuration
@Getter
public class StorageProperties {
	@Value("${application.folderUpload}")
    private String folderUpload;
    
	@Value("${application.template}")
    private String template;
}
