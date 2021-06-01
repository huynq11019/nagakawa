package com.nagakawa.guarantee.security.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	@NotNull
	private String username;

	@NotNull
	private String password;

	private Boolean rememberMe;

	@Override
	public String toString() {
		return "LoginRequest{" + "username='" + username + '\'' + ", rememberMe=" + rememberMe + '}';
	}
}
