/*
 * KaptchaService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service;

import java.util.Map;
import com.nagakawa.guarantee.model.dto.CaptchaDTO;

/**
 * 07/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface KaptchaService {
	CaptchaDTO generate();
	
	Map<String, Object> generateRequired();
	
	boolean validate(String transactionId, String text);
}
