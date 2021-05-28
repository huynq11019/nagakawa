package com.nagakawa.guarantee.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {

    private String message;

    private String code;
}
