package com.nagakawa.guarantee.service;

public interface EmailService {
	void sendResetPasswordEmail(String to, String otp, int duration);
	
//	void sendVerifyAccountEmail(String to, String otp, int duration);
}
