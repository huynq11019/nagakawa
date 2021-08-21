package com.nagakawa.guarantee.cache;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "cache")
@Getter
@Setter
public class CacheProperties {
    private String localIp;

    private String remoteIp;

    private Integer timeToLiveSeconds;

    private int backupCount;

    private ManagementCenter managementCenter;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ManagementCenter {
        private boolean enabled;
        private int updateInterval;
        private String url;
    }
}
