package com.nagakawa.guarantee.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OtpValue {

	private String otp;
	
	private int count;
	
	public OtpValue(String otp) {
		this.otp = otp;
		this.count = 1;
	}
	
	public OtpValue(String otp, int count) {
		this.otp = otp;
		this.count = count + 1;
	}
	
	public static enum OtpType {
		// Đăng ký tiêm vắc xin
		VACCINATION_REGISTRATION,
		
		// Tra cứu đăng ký
		VACCINATION_REGISTRATION_LOOK_UP,
		
		// đăng ký tài khoản tổ chức
		ORG_REGISTRATION,
		
		// Reset mật khẩu tổ chức
		ORG_RESET_PASSWORD,
		
		// Phê duyệt đăng ký của tổ chức
		ORG_APPROVE_REGISTRATION
	}
}
