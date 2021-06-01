package com.nagakawa.guarantee;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.nagakawa.guarantee.configuration.DefaultProfileUtil;
import com.nagakawa.guarantee.configuration.EnvConstants;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.util.StringPool;

@SpringBootApplication
@EnableConfigurationProperties
public class NagakawaGuaranteeApplication implements InitializingBean {

    private static final Logger _log = LoggerFactory.getLogger(NagakawaGuaranteeApplication.class);

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(NagakawaGuaranteeApplication.class);

        DefaultProfileUtil.addDefaultProfile(app);

        Environment env = app.run(args).getEnvironment();

        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = EnvConstants.Protocol.HTTP;

        if (env.getProperty(EnvConstants.Properties.SERVER_SSL_KEY_STORE) != null) {
            protocol = EnvConstants.Protocol.HTTPS;
        }

        String serverPort = env.getProperty(EnvConstants.Properties.SERVER_PORT);
        String contextPath = env.getProperty(EnvConstants.Properties.SERVER_SERVLET_CONTEXT_PATH);

        if (StringUtils.isEmpty(contextPath)) {
            contextPath = StringPool.SLASH;
        }

        String hostAddress = StringPool.LOCALHOST;

        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            _log.warn("The host name could not be determined, using `localhost` as fallback");
        }

        _log.info(
                "\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\t{}://localhost:{}{}\n\t"
                        + "External: \t{}://{}:{}{}\n\t"
                        + "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty(EnvConstants.Properties.SPRING_APPLICATION_NAME), protocol, serverPort, contextPath,
                protocol, hostAddress,

                serverPort, contextPath, env.getActiveProfiles());

        _log.info(Labels.getLabels(LabelKey.SERVER_STARTED));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(EnvConstants.Profile.DEVELOPMENT)
                && activeProfiles.contains(EnvConstants.Profile.PRODUCTION)) {
            _log.error("You have misconfigured your application! It should not run "
                    + "with both the 'dev' and 'prod' profiles at the same time.");
        }
    }
}
