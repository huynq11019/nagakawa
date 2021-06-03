/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagakawa.guarantee.schedule.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nagakawa.guarantee.schedule.Worker;
import com.nagakawa.guarantee.service.AccessTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author linhlh2
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExpiredTokenCleaner implements Worker{

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
