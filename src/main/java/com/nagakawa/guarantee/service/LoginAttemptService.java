/*
 * LoginAttemptService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service;

/**
 * 07/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface LoginAttemptService {
	void loginSucceeded(final String key);
	
	void loginFailed(final String key);
	
	boolean isRequiredCaptcha(final String key);
}
