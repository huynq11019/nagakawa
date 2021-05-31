/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagakawa.guarantee.schedule.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nagakawa.guarantee.schedule.Worker;
import com.nagakawa.guarantee.service.AccessTokenService;

import lombok.RequiredArgsConstructor;

/**
 *
 * @author linhlh2
 */
@EnableAsync
@Component
@RequiredArgsConstructor
public class ExpiredTokenCleaner implements Worker{

	private static final Logger _log = LoggerFactory.getLogger(ExpiredTokenCleaner.class);

	private final AccessTokenService accessTokenService;
	
	@Override
    @Scheduled(cron = "${cron.expired-token-cleaner}")
	@Async("qlnsScheduleExecutor")
	public void run() {

		try {
			System.out.println("-----ExpiredTokenCleaner thread is started--------");

			long result = accessTokenService.deleteAllExpired();
			
			if (_log.isDebugEnabled() && result > 0) {
			    _log.debug("Deleted {} token(s) has been expired", result);
			}
			
			System.out.println("-----ExpiredTokenCleaner thread is finished--------");
		} catch (Exception ex) {
			_log.error(ex.getMessage());
		}
	}
}
