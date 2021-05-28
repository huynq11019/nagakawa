package com.nagakawa.guarantee.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "cache")
@Getter
public class CacheProperties {
    private String localIp;

    private String remoteIp;

    private Integer timeToLiveSeconds;

    private int backupCount = 1;

    private ManagementCenter managementCenter;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ManagementCenter {
        private boolean enabled = false;
        private int updateInterval = 3;
        private String url = "";
    }
}
