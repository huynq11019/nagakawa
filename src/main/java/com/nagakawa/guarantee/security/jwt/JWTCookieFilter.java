package com.nagakawa.guarantee.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nagakawa.guarantee.security.util.SecurityConstants;

public class JWTCookieFilter extends OncePerRequestFilter {
	private final JWTTokenProvider jwtTokenProvider;

	public JWTCookieFilter(JWTTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {

			Cookie accessCookie = Arrays.stream(cookies)
					.filter((cookie) -> cookie.getName().equals(SecurityConstants.Cookie.ACCESS_TOKEN)).findFirst()
					.orElse(null);

			if (accessCookie != null) {
				String accessToken = accessCookie.getValue();

				String csrfToken = resolveCsrfToken(request);
				
				if (StringUtils.hasText(accessToken) && StringUtils.hasText(csrfToken)
						&& this.jwtTokenProvider.validateToken(accessToken, csrfToken)) {
					Authentication authentication =
							this.jwtTokenProvider.getAuthentication(accessToken);

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
	
	private String resolveCsrfToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.Header.AUTHORIZATION_HEADER);
        
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.Header.TOKEN_START)) {
            return bearerToken.substring(SecurityConstants.Header.TOKEN_START.length());
        }
        
        return null;
    }
}
