/*
 * KaptchaServiceImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service.impl;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.google.code.kaptcha.Producer;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nagakawa.guarantee.configuration.KaptchaProperties;
import com.nagakawa.guarantee.model.dto.CaptchaDTO;
import com.nagakawa.guarantee.service.KaptchaService;
import com.nagakawa.guarantee.util.FileUtil;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 07/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KaptchaServiceImpl implements KaptchaService {

	private final Producer captchaProducer;

	private final PasswordEncoder encoder;
	
	private final KaptchaProperties kaptchaProperties;

	private LoadingCache<String, String> captchaCache;

	@PostConstruct
	void init() {
		this.captchaCache =
				CacheBuilder.newBuilder().expireAfterWrite(kaptchaProperties.getDuration(), TimeUnit.MINUTES)
						.build(new CacheLoader<String, String>() {
							public String load(String key) {
								return StringPool.BLANK;
							}
						});
	}
	
	@Override
	public CaptchaDTO generate() {
		String capText = captchaProducer.createText();

		String transactionId = encoder.encode(capText);

		captchaCache.put(transactionId, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);

		return new CaptchaDTO(transactionId, FileUtil.getImageSrcBase64String(bi, "jpg"));
	}

	@Override
	public Map<String, Object> generateRequired() {
		String capText = captchaProducer.createText();

		String transactionId = encoder.encode(capText);

		captchaCache.put(transactionId, capText);
		// create the image with the text
		BufferedImage bi = captchaProducer.createImage(capText);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("captcha", FileUtil.getImageSrcBase64String(bi, "jpg"));
		data.put("transactionId", transactionId);
		data.put("captchaRequired", true);

		return data;
	}

	@Override
	public boolean validate(String transactionId, String text) {
		String textInCache = captchaCache.getIfPresent(transactionId);

		if (Validator.isNotNull(textInCache) && textInCache.equals(text)) {
			// invalidate captcha
			captchaCache.invalidate(transactionId);
			
			_log.info("Current captchaCache size: {} ", captchaCache.size());

			return true;
		}

		return false;
	}
}
