/*
 * KaptchaAspect.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.aop;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.api.util.HttpUtil;
import com.nagakawa.guarantee.configuration.KaptchaProperties;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.service.KaptchaService;
import com.nagakawa.guarantee.service.LoginAttemptService;
import com.nagakawa.guarantee.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 02/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class KaptchaAspect {
	private final KaptchaProperties kaptchaProperties;

	private final PasswordEncoder encoder;
	
	private final LoginAttemptService loginAttemptService;
	
	private final KaptchaService kaptchaService;

	@Before("@annotation(com.nagakawa.guarantee.annotation.RequiredKaptcha)")
	public void requiredKaptcha() throws Throwable {
		String headerName = kaptchaProperties.getHeaderName();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		
		String captcha = request.getHeader(headerName);

		String transactionId = request.getHeader(ApiConstants.HttpHeaders.X_TRANSACTION_ID);

		_log.info("captcha: " + captcha);
		_log.info("transactionId: " + transactionId);
		
		if (Validator.isNull(captcha) || Validator.isNull(transactionId)
				|| !encoder.matches(captcha, transactionId)
				|| !kaptchaService.validate(transactionId, captcha)) {
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INCORRECT_CAPTCHA),
					ApiConstants.EntityName.CAPTCHA, LabelKey.ERROR_INCORRECT_CAPTCHA);
		}
	}
	
	@Before("@annotation(com.nagakawa.guarantee.annotation.LoginKaptcha)")
	public void loginKaptcha() throws Throwable {
		String headerName = kaptchaProperties.getHeaderName();

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		//check nếu ip đang không bị block thì bỏ qua
		if (!loginAttemptService.isRequiredCaptcha(HttpUtil.getClientIP(request))) {
			return;
		}
		
		String captcha = request.getHeader(headerName);

		String transactionId = request.getHeader(ApiConstants.HttpHeaders.X_TRANSACTION_ID);

		_log.info("captcha: " + captcha);
		_log.info("transactionId: " + transactionId);
		
		if (Validator.isNull(captcha) || Validator.isNull(transactionId)
				|| !encoder.matches(captcha, transactionId)
				|| !kaptchaService.validate(transactionId, captcha)) {
			Map<String, Object> params = kaptchaService.generateRequired();
			
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INCORRECT_CAPTCHA),
					ApiConstants.EntityName.CAPTCHA, LabelKey.ERROR_INCORRECT_CAPTCHA, params);
		}
	}
}
