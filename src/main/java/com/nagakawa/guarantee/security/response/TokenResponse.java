package com.nagakawa.guarantee.security.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@JsonInclude(Include.NON_NULL)
public class TokenResponse {
    private String accessToken;
    
    private String refreshToken;
    
    private String tokenType;
    
    private int accessTokenDuration;
    
    private int refreshTokenDuration;
}
