package com.nagakawa.guarantee.security.api;

import java.time.Instant;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagakawa.guarantee.api.exception.BadRequestAlertException;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.UserLogin;
import com.nagakawa.guarantee.model.dto.UserDTO;
import com.nagakawa.guarantee.security.jwt.JWTToken;
import com.nagakawa.guarantee.security.jwt.JWTTokenProvider;
import com.nagakawa.guarantee.security.request.LoginRequest;
import com.nagakawa.guarantee.security.request.TokenRefreshRequest;
import com.nagakawa.guarantee.security.response.TokenResponse;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.service.UserLoginService;
import com.nagakawa.guarantee.service.UserService;
import com.nagakawa.guarantee.service.mapper.UserMapper;
import com.nagakawa.guarantee.util.Constants;
import com.nagakawa.guarantee.util.GetterUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller to authenticate users.
 */
@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserJWTController {
    private final JWTTokenProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserService userService;

    private final UserLoginService userLoginService;

    private final UserMapper userMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authorize(HttpServletRequest request,
            @Valid @RequestBody LoginRequest loginRequest) {

        String username = GetterUtil.getString(loginRequest.getUsername());
        String password = GetterUtil.getString(loginRequest.getPassword());

        Optional<User> optionalUser = this.userService.findByUsername(username);

        if (!optionalUser.isPresent() || Constants.EntityStatus.ACTIVE != optionalUser.get().getStatus()) {
            new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INVALID_USER_OR_PASSWORD),
                    User.class.getSimpleName(), LabelKey.ERROR_INVALID_USER_OR_PASSWORD);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);

        try {
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            boolean rememberMe = (loginRequest.getRememberMe() == null) ? false : loginRequest.getRememberMe();

            JWTToken accessToken = jwtTokenProvider.createAccessToken(authentication, rememberMe);

            JWTToken refreshToken = jwtTokenProvider.createRefreshToken(authentication);

            HttpHeaders httpHeaders = new HttpHeaders();

            httpHeaders.add(SecurityConstants.Jwt.AUTHORIZATION_HEADER,
                    SecurityConstants.Jwt.TOKEN_START + accessToken);

            TokenResponse tokenResponse = TokenResponse.builder().tokenType(SecurityConstants.Jwt.TOKEN_START.trim())
                    .accessToken(accessToken.getToken()).accessTokenExpiredAt(accessToken.getExpiredTime())
                    .refreshToken(refreshToken.getToken()).refreshTokenExpiredAt(refreshToken.getExpiredTime()).build();

            // save login success
            this.saveUserLogin(authentication.getName(), request.getLocalAddr(), true,
                    Labels.getLabels(LabelKey.LOGIN_SUCCESSFUL));

            return new ResponseEntity<>(tokenResponse, httpHeaders, HttpStatus.OK);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            _log.error(e.getMessage());

            // save login failure
            this.saveUserLogin(username, request.getLocalAddr(), false,
                    Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD));

            throw new BadCredentialsException(Labels.getLabels(LabelKey.ERROR_INVALID_USERNAME_OR_PASSWORD));
        }
    }

    @GetMapping("/authenticate")
    public String isAuthenticated(HttpServletRequest request) {
        return request.getRemoteUser();
    }

    @GetMapping("/authenticate/account")
    public UserDTO getAccount() {
        return userService.getUserWithRoles().map(this.userMapper::toDto).orElseThrow(
                () -> new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_USER_COULD_NOT_BE_FOUND),
                        User.class.getSimpleName(), LabelKey.ERROR_USER_COULD_NOT_BE_FOUND));
    }

    @GetMapping("/authenticate/logout")
    public ResponseEntity<Boolean> logout() {
        jwtTokenProvider.invalidateToken();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/authenticate/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest refreshRequest,
            HttpServletRequest request) {
        String refreshToken = refreshRequest.getRefreshToken();

        String username = refreshRequest.getUsername();

        return jwtTokenProvider.refreshToken(username, refreshToken)
                .map(accessToken -> new ResponseEntity<TokenResponse>(TokenResponse.builder()
                        .tokenType(SecurityConstants.Jwt.TOKEN_START.trim()).accessToken(accessToken.getToken())
                        .accessTokenExpiredAt(accessToken.getExpiredTime()).refreshToken(refreshToken).build(),
                        HttpStatus.OK))
                .orElseThrow(() -> new BadRequestAlertException(Labels.getLabels(LabelKey.ERROR_INVALID_REFRESH_TOKEN),
                        User.class.getSimpleName(), LabelKey.ERROR_INVALID_REFRESH_TOKEN));
    }

    private void saveUserLogin(String username, String ip, boolean success, String description) {
        UserLogin loginLog = UserLogin.builder()//
                .username(username).ip(ip).loginTime(Instant.now()).success(success).description(description).build();

        userLoginService.save(loginLog);
    }
}
