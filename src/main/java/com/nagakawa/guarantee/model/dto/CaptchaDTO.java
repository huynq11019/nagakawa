/*
 * SingleDataDTO.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 02/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Getter
@Setter
@AllArgsConstructor
public class CaptchaDTO {
	private String transactionId;
	
	private String captcha;
}
