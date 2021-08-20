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
		// Reset mật khẩu
		RESET_PASSWORD,
	}
}
