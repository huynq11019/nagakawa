/*
 * JWTToken.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 02/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Data
@AllArgsConstructor
public class JWTToken {
    private String token;
    
    private int duration;
}
