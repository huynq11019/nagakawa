package com.nagakawa.guarantee.service;

import com.nagakawa.guarantee.model.dto.OtpValue;
import com.nagakawa.guarantee.model.dto.OtpValue.OtpType;

public interface OtpService {
	OtpValue findByKey(String phoneNumber, OtpType type);
	
	void invalidateOtp(String phoneNumber, OtpType type);
	
	void sendOtpViaSms(String phoneNumber, OtpType type);
	
	void validateOtp(String phoneNumber, OtpType type, String otp);
}
