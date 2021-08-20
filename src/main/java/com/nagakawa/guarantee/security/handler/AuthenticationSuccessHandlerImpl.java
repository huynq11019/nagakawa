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
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;
import com.nagakawa.guarantee.model.UserLogin;
import com.nagakawa.guarantee.service.UserLoginService;
import lombok.RequiredArgsConstructor;

/**
 * @author linhlh2
 */
@RequiredArgsConstructor
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final UserLoginService userLoginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // Save authentication failure to user login table
        UserLogin loginLog = UserLogin.builder().username(authentication.getName()).ip(request.getRemoteAddr())
                .loginTime(Instant.now()).success(true).description(Labels.getLabels(LabelKey.MESSAGE_LOGIN_SUCCESSFUL))
                .build();

        userLoginService.save(loginLog);

    }
}
