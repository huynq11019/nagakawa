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
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.dto.UserDTO;
import com.nagakawa.guarantee.security.jwt.JWTFilter;
import com.nagakawa.guarantee.security.jwt.JwtTokenProvider;
import com.nagakawa.guarantee.security.request.LoginRequest;
import com.nagakawa.guarantee.security.request.TokenRefreshRequest;
import com.nagakawa.guarantee.security.response.TokenResponse;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.service.UserService;
import com.nagakawa.guarantee.service.mapper.UserMapper;
import com.nagakawa.guarantee.util.Constants;
import com.nagakawa.guarantee.util.Validator;

import liquibase.pro.packaged.iF;
import lombok.RequiredArgsConstructor;

/**
 * Controller to authenticate users.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserJWTController {
	private final JwtTokenProvider jwtTokenProvider;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final UserService userService;
	
	private final UserMapper userMapper;

	@PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authorize(@Valid @RequestBody LoginRequest loginRequest) {
        Optional<User> optionalUser = this.userService.findByUsername(loginRequest.getUsername().trim());

        if (!optionalUser.isPresent() || Constants.EntityStatus.ACTIVE != optionalUser.get().getStatus()) {
        	new BadRequestAlertException("Invalid username or password", User.class.getSimpleName(),
                    "error.invalid-user-or-password");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername().trim(), loginRequest.getPassword().trim());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = (loginRequest.getRememberMe() == null) ? false : loginRequest.getRememberMe();

        String accessToken = jwtTokenProvider.createAccessToken(authentication, rememberMe);

        String refreshToken = jwtTokenProvider.createRefreshToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(SecurityConstants.Jwt.AUTHORIZATION_HEADER, SecurityConstants.Jwt.TOKEN_START + accessToken);

        return new ResponseEntity<>(new TokenResponse(accessToken, refreshToken, SecurityConstants.Jwt.TOKEN_START.trim()),
                httpHeaders, HttpStatus.OK);
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
	
	@GetMapping("/logout")
	public ResponseEntity<Boolean> logout(HttpServletRequest request) {
	    jwtTokenProvider.invalidateToken();
	    
	    return ResponseEntity.ok().build();
	}
	
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest,
            HttpServletRequest request) {
        String refreshToken = refreshRequest.getRefreshToken();

        String username = refreshRequest.getUsername();

        return jwtTokenProvider.refreshToken(username, refreshToken)
                .map(accessToken -> new ResponseEntity<TokenResponse>(
                        new TokenResponse(accessToken, refreshToken, SecurityConstants.Jwt.TOKEN_START.trim()),
                        HttpStatus.OK))
                .orElseThrow(() -> new BadRequestAlertException("Invalid refresh token", User.class.getSimpleName(),
                        "error.invalid-refresh-token"));
    }
}
