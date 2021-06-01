package com.nagakawa.guarantee.configuration;

public interface EnvConstants {

    public interface Profile {
        public static final String DEVELOPMENT = "dev";

        public static final String PRODUCTION = "prod";
        
        public static final String DEFAULT = "spring.profiles.default";
    }

    public interface Properties {
        public static final String SERVER_PORT = "server.port";

        public static final String SERVER_SERVLET_CONTEXT_PATH = "server.servlet.context-path";

        public static final String SERVER_SSL_KEY_STORE = "server.ssl.key-store";

        public static final String SPRING_APPLICATION_NAME = "spring.application.name";
    }

    public interface Protocol {
        public static final String HTTP = "http";

        public static final String HTTPS = "https";
    }
}
