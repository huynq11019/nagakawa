package com.nagakawa.guarantee.configuration;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import lombok.Getter;


@ConfigurationProperties(prefix = "ftp")
@Getter
public class FTPProperties {
    private String server;
    private String username;
    private String password;
    @Min(0)
    @Max(65535)
    private int port;
    private int keepAliveTimeout;
    private boolean autoStart;

    @PostConstruct
    public void init() {
        if (port == 0) {
            port = 21;
        }
    }
}
