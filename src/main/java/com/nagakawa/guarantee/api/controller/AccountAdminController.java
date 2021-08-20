package com.nagakawa.guarantee.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nagakawa.guarantee.annotation.LoginKaptcha;
import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.util.ApiConstants;
import com.nagakawa.guarantee.api.util.HttpUtil;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.AccountDTO;
import com.nagakawa.guarantee.security.RsaProvider;
import com.nagakawa.guarantee.security.jwt.JWTToken;
import com.nagakawa.guarantee.security.jwt.JWTTokenProvider;
import com.nagakawa.guarantee.security.request.LoginRequest;
import com.nagakawa.guarantee.security.response.TokenResponse;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.service.KaptchaService;
import com.nagakawa.guarantee.service.LoginAttemptService;
import com.nagakawa.guarantee.service.UserLoginService;
import com.nagakawa.guarantee.service.UserService;
import com.nagakawa.guarantee.util.Constants;
import com.nagakawa.guarantee.util.GetterUtil;
import com.nagakawa.guarantee.util.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to authenticate users.
 */
@Slf4j
@RestController
@RequestMapping("/api/account/admin")
@RequiredArgsConstructor
public class AccountAdminController {
	private final JWTTokenProvider jwtTokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserService userService;

	private final UserLoginService userLoginService;

	private final LoginAttemptService loginAttemptService;

	private final RsaProvider rsaProvider;

	private final KaptchaService kaptchaService;

	@LoginKaptcha
	@PostMapping("/login")
	public ResponseEntity<TokenResponse> authorize(HttpServletRequest request,
			HttpServletResponse response, @Valid @RequestBody LoginRequest loginRequest) {

		String username = GetterUtil.getString(loginRequest.getUsername());
		String password = GetterUtil.getString(loginRequest.getPassword());

		String ip = HttpUtil.getClientIP(request);

		try {
			if (Validator.isNull(username) || Validator.isNull(password)) {
				new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD),
						ApiConstants.EntityName.ACCOUNT, LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD);
			}

			Optional<User> optionalUser = this.userService.findByUsername(username);

			if (!optionalUser.isPresent()
					|| Constants.EntityStatus.ACTIVE != optionalUser.get().getStatus()) {
				throw new BadRequestAlertException(
						Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD),
						ApiConstants.EntityName.ACCOUNT, LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD);
			}
			
			password = rsaProvider.decrypt(password);

			UsernamePasswordAuthenticationToken authenticationToken =
					new UsernamePasswordAuthenticationToken(username, password);

			Authentication authentication =
					authenticationManagerBuilder.getObject().authenticate(authenticationToken);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			boolean rememberMe =
					(loginRequest.getRememberMe() == null) ? false : loginRequest.getRememberMe();

			JWTToken accessToken = jwtTokenProvider.createAccessToken(authentication, rememberMe);

			JWTToken refreshToken = jwtTokenProvider.createRefreshToken(authentication);

			HttpHeaders httpHeaders = new HttpHeaders();

			TokenResponse tokenResponse =
					TokenResponse.builder().tokenType(SecurityConstants.Header.TOKEN_START.trim())
							.accessToken("true")
							.accessTokenDuration(accessToken.getDuration())
							.refreshToken("true")
							.refreshTokenDuration(refreshToken.getDuration()).build();

			// save login success
			userLoginService.saveUserLogin(authentication.getName(), request.getLocalAddr(), true,
					Labels.getLabels(LabelKey.MESSAGE_LOGIN_SUCCESSFUL));

			// clear login failt attempt
			loginAttemptService.loginSucceeded(ip);

			HttpCookie accessCookie =
					jwtTokenProvider.createHttpCookie(SecurityConstants.Cookie.ACCESS_TOKEN,
							accessToken.getToken(), accessToken.getDuration());

			HttpCookie refreshCookie =
					jwtTokenProvider.createHttpCookie(SecurityConstants.Cookie.REFRESH_TOKEN,
							refreshToken.getToken(), refreshToken.getDuration());

			httpHeaders.add(HttpHeaders.SET_COOKIE, accessCookie.toString());
			httpHeaders.add(HttpHeaders.SET_COOKIE, refreshCookie.toString());
			httpHeaders.add(SecurityConstants.Header.AUTHORIZATION_HEADER, username);

			return new ResponseEntity<>(tokenResponse, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			_log.error("Admin login failured with user name {}", username);

			// save login failure
			userLoginService.saveUserLogin(username, request.getLocalAddr(), false,
					Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD));

			loginAttemptService.loginFailed(ip);

			Map<String, Object> params =
					loginAttemptService.isRequiredCaptcha(ip) ? kaptchaService.generateRequired()
							: new HashMap<>();

			throw new BadRequestAlertException(
					Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD),
					ApiConstants.EntityName.ACCOUNT, LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD,
					params);
		}
	}

	@GetMapping("/info")
	public AccountDTO getAccount() {
		return userService.getUserInfo();
	}

	@GetMapping("/logout")
	public ResponseEntity<Boolean> logout(HttpServletResponse response) {
		jwtTokenProvider.invalidateToken();

		response.addCookie(jwtTokenProvider.clearCookie(SecurityConstants.Cookie.ACCESS_TOKEN));
		response.addCookie(jwtTokenProvider.clearCookie(SecurityConstants.Cookie.REFRESH_TOKEN));

		return ResponseEntity.ok().build();
	}
}
