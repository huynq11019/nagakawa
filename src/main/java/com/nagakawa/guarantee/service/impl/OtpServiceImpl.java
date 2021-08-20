package com.nagakawa.guarantee.service.impl;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.configuration.OtpProperties;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.dto.OtpValue;
import com.nagakawa.guarantee.model.dto.OtpValue.OtpType;
import com.nagakawa.guarantee.service.OtpService;
import com.nagakawa.guarantee.service.SmsService;
import com.nagakawa.guarantee.util.RandomGenerator;
import com.nagakawa.guarantee.util.Constants;
import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.Validator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OtpServiceImpl implements OtpService {
	private final LoadingCache<String, OtpValue> otpCache;

	private final OtpProperties otpProperties;
	
	@Autowired
	private SmsService smsService;

	@Autowired
	public OtpServiceImpl(OtpProperties otpProperties) {
		super();

		this.otpProperties = otpProperties;

		this.otpCache = CacheBuilder.newBuilder().expireAfterWrite(otpProperties.getExpireTime(), TimeUnit.SECONDS)
				.build(new CacheLoader<String, OtpValue>() {
					public OtpValue load(String key) {
						return new OtpValue();
					}
				});
	}

	@Override
	public OtpValue findByKey(String phoneNumber, OtpType type) {
		return otpCache.getIfPresent(createKey(phoneNumber, type));
	}

	@Override
	public void invalidateOtp(String phoneNumber, OtpType type) {
		otpCache.invalidate(createKey(phoneNumber, type));
	}

	@Override
	public void sendOtpViaSms(String phoneNumber, OtpType type) {
		// check số điện thoại có đúng định dạng ko
		if (!Validator.isVNPhoneNumber(phoneNumber)) {
			throw new BadRequestAlertException(
					Labels.getLabels(LabelKey.ERROR_INVALID_DATA_FORMAT,
							new Object[] {Labels.getLabels(LabelKey.LABEL_PHONE_NUMBER)}),
					ApiConstants.EntityName.OTP, LabelKey.ERROR_INVALID_DATA_FORMAT);
		}

		// Kiểm tra số lần gửi otp
		OtpValue otpValue = this.findByKey(phoneNumber, type);

		if (Validator.isNotNull(otpValue) && otpValue.getCount() >= otpProperties.getOtpAttempt()) {
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_LIMIT_OTP_ATTEMPTS),
					ApiConstants.EntityName.OTP, LabelKey.ERROR_LIMIT_OTP_ATTEMPTS);
		}

		if (otpProperties.isEnable()) {
			// Tao tin nhan de gui
			String otp = generateOtp(phoneNumber, type);

			_log.info("Otp: {}", otp);

			String message = String.format(otpProperties.getTemplate(), otp);

			SmsServiceImpl.SmsResponse smsResponse = smsService.send(phoneNumber, message);

			if (smsResponse.getResult() == Constants.SMSStatus.ERROR) {
				invalidateOtp(phoneNumber, type);

				_log.error("Gui tin nhan that bai: {}", smsResponse.getMessage());

				throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_OTP_IS_NOT_SENT),
						ApiConstants.EntityName.OTP, LabelKey.ERROR_OTP_IS_NOT_SENT);
			}
		} else {
			// Setup de test
			setOtp(phoneNumber, type, otpProperties.getDefaultOtp(),
					Validator.isNotNull(otpValue) ? otpValue.getCount() : 0);
		}
	}
	
	@Override
	public void validateOtp(String phoneNumber, OtpType type, String otp) {
		if (Validator.isNull(otp)) {
			_log.error("OTP to phone number {} is null", phoneNumber);
			
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_OTP_IS_INCORRECT_OR_HAS_EXPIRED),
					ApiConstants.EntityName.OTP, LabelKey.ERROR_OTP_IS_INCORRECT_OR_HAS_EXPIRED);
		}

		OtpValue value = this.findByKey(phoneNumber, type);

		if (value == null || !value.getOtp().equals(otp)) {
			_log.error("OTP {} to phone number {} is incorrect or expired", otp, phoneNumber);
			
			throw new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_OTP_IS_INCORRECT_OR_HAS_EXPIRED),
					ApiConstants.EntityName.OTP, LabelKey.ERROR_OTP_IS_INCORRECT_OR_HAS_EXPIRED);
		}
	}
	
	private String createKey(String phoneNumber, OtpType type) {
		StringBuilder sb = new StringBuilder(3);

		sb.append(phoneNumber);
		sb.append(StringPool.DASH);
		sb.append(type.name());
		
		return sb.toString();
	}


	private String generateOtp(String phoneNumber, OtpType type) {
		String key = createKey(phoneNumber, type);

		OtpValue value = otpCache.getIfPresent(key);

		String otp = RandomGenerator.randomWithNDigits(otpProperties.getNumberOfDigits());

		if (value != null) {
			while (value.getOtp().equals(otp)) {
				otp = RandomGenerator.randomWithNDigits(otpProperties.getNumberOfDigits());
			}
		}

		OtpValue newOtp = Validator.isNotNull(value) ? new OtpValue(otp, value.getCount()) : new OtpValue(otp);

		System.out.println("======Testing======");
		System.out.println("Otp Value: " + otp);

		otpCache.put(key, newOtp);

		return otp;
	}

	private void setOtp(String phoneNumber, OtpType type, String otp, int count) {
		OtpValue value = new OtpValue(otp, count);

		otpCache.put(createKey(phoneNumber, type), value);
	}
}
