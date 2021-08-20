/*
 * KaptchaProperties.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.configuration;

import java.util.Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import lombok.Data;

/**
 * 02/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Data
@Component
@ConfigurationProperties(prefix = "kaptcha.config")
public class KaptchaProperties {
	private String imageWidth;

	private String imageHeight;

	private String textProducerCharString;

	private String textProducerCharLength;

	private String textproducerFontSize;

	private String textproducerCharSpace;

	private String textproducerFontNames;

	private String textproducerFontColor;

	private String backgroundClearFrom;

	private String backgroundClearTo;

	private String headerName;

	private String useBorder;
	
	private int duration;

	@Bean
	public Producer createKaptchaProducer() {
		DefaultKaptcha kaptcha = new DefaultKaptcha();

		Properties properties = new Properties();

		properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, imageHeight);
		properties.put(Constants.KAPTCHA_IMAGE_WIDTH, imageWidth);
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, textProducerCharLength);
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, textProducerCharString);
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, textproducerFontSize);
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, textproducerCharSpace);
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, textproducerFontNames);
		properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, textproducerFontColor);
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, backgroundClearFrom);
		properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, backgroundClearTo);
		properties.put(Constants.KAPTCHA_BORDER, useBorder);

		properties.put(Constants.KAPTCHA_NOISE_COLOR, textproducerFontColor);

		kaptcha.setConfig(new Config(properties));

		return kaptcha;
	}
}
