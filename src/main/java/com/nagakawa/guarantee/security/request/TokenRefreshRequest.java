package com.nagakawa.guarantee.security.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TokenRefreshRequest {
  @NotBlank
  private String refreshToken;
  
  @NotBlank
  private String username;
}
