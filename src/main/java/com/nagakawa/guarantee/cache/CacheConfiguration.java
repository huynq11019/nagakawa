package com.nagakawa.guarantee.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.ManagementCenterConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.nagakawa.guarantee.configuration.EnvConstants;

@Configuration
@EnableCaching
public class CacheConfiguration implements DisposableBean {
    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);
    
    @Autowired
    private Environment env;
    
    @Autowired
    private CacheProperties properties;

    @Override
    public void destroy() throws Exception {
        log.info("Closing Cache Manager");
        
        Hazelcast.shutdownAll();
    }

    @Bean
    public CacheManager cacheManager(HazelcastInstance hazelcastInstance) {
        log.info("Starting HazelcastCacheManager");
        
        return new com.hazelcast.spring.cache.HazelcastCacheManager(hazelcastInstance);
    }

    @Bean
    public HazelcastInstance hazelcastInstance() {
        log.info("Configuring Hazelcast");
        
        HazelcastInstance hazelCastInstance = Hazelcast.getHazelcastInstanceByName("nagakawa_guarantee");
        
        if (hazelCastInstance != null) {
            log.info("Hazelcast already initialized");
            
            return hazelCastInstance;
        }
        
        Config config = new Config();
        
        config.setInstanceName("nagakawa_guarantee");
        config.getNetworkConfig().setPort(5704);
        config.getNetworkConfig().setPortAutoIncrement(true);
        // In development, remove multicast auto-configuration
        System.setProperty("hazelcast.local.localAddress", properties.getLocalIp());
        
        if (env.acceptsProfiles(Profiles.of(EnvConstants.SPRING_PROFILE_DEVELOPMENT))) {
//            System.setProperty("hazelcast.local.localAddress", properties.getIp());
            config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true).addMember(properties.getRemoteIp());
        } else {
            config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
            config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
        }
        
        config.getMapConfigs().put("default", initializeDefaultMapConfig(properties));
        // Full reference is available at: http://docs.hazelcast.org/docs/management-center/3
        // .9/manual/html/Deploying_and_Starting.html
        config.setManagementCenterConfig(initializeDefaultManagementCenterConfig(properties));
        config.getMapConfigs().put("com.nagakawa.guarantee.model.*", initializeDomainMapConfig(properties));
//        config.getCPSubsystemConfig()
//                .setCPMemberCount(7)
//                .setGroupSize(3)
//                .setSessionTimeToLiveSeconds(300)
//                .setSessionHeartbeatIntervalSeconds(5)
//                .setMissingCPMemberAutoRemovalSeconds(14400)
//                .setFailOnIndeterminateOperationState(false);
//        config.getCPSubsystemConfig()
//                .addLockConfig(new FencedLockConfig("reentrant-lock", 0))
//                .addLockConfig(new FencedLockConfig("limited-reentrant-lock", 10))
//                .addLockConfig(new FencedLockConfig("non-reentrant-lock", 1));
        return Hazelcast.newHazelcastInstance(config);
    }

    private ManagementCenterConfig initializeDefaultManagementCenterConfig(CacheProperties properties) {
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        
        managementCenterConfig.setEnabled(properties.getManagementCenter().isEnabled());
        managementCenterConfig.setUrl(properties.getManagementCenter().getUrl());
        managementCenterConfig.setUpdateInterval(properties.getManagementCenter().getUpdateInterval());
        
        return managementCenterConfig;
    }

    private MapConfig initializeDefaultMapConfig(CacheProperties properties) {
        MapConfig mapConfig = new MapConfig();
        /*
        Number of backups. If 1 is set as the backup-count for example,
        then all entries of the map will be copied to another JVM for
        fail-safety. Valid numbers are 0 (no backup), 1, 2, 3.
        */
        mapConfig.setBackupCount(properties.getBackupCount());
        /*
        Valid values are:
        NONE (no eviction),
        LRU (Least Recently Used),
        LFU (Least Frequently Used).
        NONE is the default.
        */
        mapConfig.setEvictionPolicy(EvictionPolicy.LRU);
        /*
        Maximum size of the map. When max size is reached,
        map is evicted based on the policy defined.
        Any integer between 0 and Integer.MAX_VALUE. 0 means
        Integer.MAX_VALUE. Default is 0.
        */
        mapConfig.setMaxSizeConfig(new MaxSizeConfig(0, MaxSizeConfig.MaxSizePolicy.USED_HEAP_SIZE));
        return mapConfig;
    }

    private MapConfig initializeDomainMapConfig(CacheProperties properties) {
        MapConfig mapConfig = new MapConfig();
        
        mapConfig.setTimeToLiveSeconds(properties.getTimeToLiveSeconds());
        
        return mapConfig;
    }
}
