package com.nagakawa.guarantee.security.response;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class TokenResponse {
    private String accessToken;
    
    private String refreshToken;
    
    private String tokenType;
    
    private Instant accessTokenExpiredAt;
    
    private Instant refreshTokenExpiredAt;
}
