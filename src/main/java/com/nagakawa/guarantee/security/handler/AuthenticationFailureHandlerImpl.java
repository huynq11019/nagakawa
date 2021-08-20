/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nagakawa.guarantee.security.handler;

import java.io.IOException;
import java.time.Instant;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import com.nagakawa.guarantee.model.UserLogin;
import com.nagakawa.guarantee.service.UserLoginService;
import lombok.RequiredArgsConstructor;

/**
 * @author linhlh2
 */
@RequiredArgsConstructor
@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private final UserLoginService userLoginService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        // Save authentication failure to user login table
        UserLogin loginLog = UserLogin.builder().username(request.getUserPrincipal().getName())
                .ip(request.getRemoteAddr()).loginTime(Instant.now()).success(false).description(exception.getMessage())
                .build();

        userLoginService.save(loginLog);

    }

}
