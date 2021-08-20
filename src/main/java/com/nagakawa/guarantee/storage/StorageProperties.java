package com.nagakawa.guarantee.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

/**
 * @author LinhLH
 */
@Data
@Component
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {
    private String folderUpload;
    
    private String template;
    
    private int fileKeyStoreDuration;
}
