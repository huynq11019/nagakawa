/**
 * 
 */
package com.nagakawa.guarantee.schedule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.nagakawa.guarantee.schedule.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author LinhLH
 *
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "scheduling.evict-all-caches", name="enabled", havingValue="true", matchIfMissing = false)
public class CacheCleaner implements Worker {

	@Autowired
	private CacheManager cacheManager;

	@Override
	@Scheduled(cron = "${scheduling.evict-all-caches.cron}")
	@Async("nagakawaScheduleExecutor")
	public void run() {
		try {
			_log.info("-----CacheCleaner thread is started--------");

			cacheManager.getCacheNames().stream().forEach(cacheName -> cacheManager.getCache(cacheName).clear());

			_log.info("-----CacheCleaner thread is finished--------");
		} catch (Exception ex) {
			_log.error(ex.getMessage());
		}
	}

}
