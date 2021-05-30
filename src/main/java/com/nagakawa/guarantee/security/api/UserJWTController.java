package com.nagakawa.guarantee.security.api;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.api.request.LoginRequest;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.UserDTO;
import com.nagakawa.guarantee.security.jwt.JWTFilter;
import com.nagakawa.guarantee.security.jwt.JWTToken;
import com.nagakawa.guarantee.security.jwt.JwtTokenProvider;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.service.UserService;
import com.nagakawa.guarantee.service.mapper.UserMapper;
import com.nagakawa.guarantee.util.Constants;

import lombok.RequiredArgsConstructor;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserJWTController {
	private final JwtTokenProvider jwtTokenUtil;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserService userService;
	
	private final UserMapper userMapper;

	@PostMapping("/authenticate")
	public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginRequest loginRequest) {
		Optional<User> optionalUser = this.userService.findByUsername(loginRequest.getUsername().trim());

		if (!optionalUser.isPresent() || Constants.EntityStatus.ACTIVE != optionalUser.get().getStatus()) {
			throw new BadCredentialsException("Bad credentials");
		}

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername().trim(), loginRequest.getPassword().trim());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		boolean rememberMe = (loginRequest.getRememberMe() == null) ? false : loginRequest.getRememberMe();

		String jwt = jwtTokenUtil.createToken(authentication, rememberMe);

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, SecurityConstants.Jwt.TOKEN_START + jwt);

		return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
	}
	
	@GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }
	
	@GetMapping("/account")
	public UserDTO getAccount() {
		return userService.getUserWithRoles().map(this.userMapper::toDto)
				.orElseThrow(() -> new BadRequestAlertException("User could not be found", User.class.getSimpleName(),
						"error.user-could-not-be-found"));
	}
}
