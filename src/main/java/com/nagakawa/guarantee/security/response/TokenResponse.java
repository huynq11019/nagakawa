package com.nagakawa.guarantee.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TokenResponse {
    private String accessToken;
    
    private String refreshToken;
    
    private String tokenType;
}
