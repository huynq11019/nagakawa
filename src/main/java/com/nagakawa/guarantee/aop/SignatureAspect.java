/*
 * SignatureAspect.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.aop;

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.security.RsaProvider;
import com.nagakawa.guarantee.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 14/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class SignatureAspect {
	private final RsaProvider signalRsaProvider;
	
	@Before("@annotation(com.nagakawa.guarantee.annotation.Signature)")
	public void requiredKaptcha() throws Throwable {
		HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
						.getRequest();

		String transactionId = request.getHeader(ApiConstants.HttpHeaders.X_TRANSACTION_ID);
		String signature = request.getHeader(ApiConstants.HttpHeaders.X_SIGNATURE);

		_log.info("signature: " + signature);
		_log.info("transactionId: " + transactionId);

		if (Validator.isNull(signature) || Validator.isNull(transactionId)) {
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INCORRECT_SIGNATURE),
					ApiConstants.EntityName.SIGNATURE, LabelKey.ERROR_INCORRECT_SIGNATURE);
		}

		String transactionIdDecode = signalRsaProvider.decrypt(signature);

		if (!transactionIdDecode.equals(transactionId)) {
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INCORRECT_SIGNATURE),
					ApiConstants.EntityName.SIGNATURE, LabelKey.ERROR_INCORRECT_SIGNATURE);
		}
	}
}
