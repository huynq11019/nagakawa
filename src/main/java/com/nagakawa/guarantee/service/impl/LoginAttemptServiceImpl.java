/*
 * LoginAttemptHandlerImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nagakawa.guarantee.security.configuration.AuthenticationProperties;
import com.nagakawa.guarantee.service.LoginAttemptService;
import lombok.extern.slf4j.Slf4j;

/**
 * 07/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Service
public class LoginAttemptServiceImpl implements LoginAttemptService {
	private final AuthenticationProperties authenticationProperties;

	private final LoadingCache<String, Integer> attemptsCache;

	@Autowired
	public LoginAttemptServiceImpl(AuthenticationProperties authenticationProperties) {
		super();

		this.authenticationProperties = authenticationProperties;
		
		this.attemptsCache = CacheBuilder.newBuilder()
				.expireAfterWrite(authenticationProperties.getLoginBlockExpiredAfter(), TimeUnit.DAYS)
				.build(new CacheLoader<String, Integer>() {
					@Override
					public Integer load(final String key) {
						return 0;
					}
				});
	}

	@Override
	public void loginSucceeded(final String key) {
		attemptsCache.invalidate(key);
	}

	@Override
	public void loginFailed(final String key) {
		int attempts = 0;

		try {
			attempts = attemptsCache.get(key);

		} catch (final ExecutionException e) {
			attempts = 0;
		}

		attempts++;

		_log.warn("User with ip {} login failure for {} times", key, attempts);

		attemptsCache.put(key, attempts);
	}

	@Override
	public boolean isRequiredCaptcha(final String key) {
		try {
			return attemptsCache.get(key) >= authenticationProperties.getLoginMaxAttemptTime();
		} catch (final ExecutionException e) {
			return false;
		}
	}
}
