/*
 * AccessTokenService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service;

import java.util.Optional;

import com.nagakawa.guarantee.model.AccessToken;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface AccessTokenService {
    long deleteAllExpired();

    /**
     * @param username
     * @return
     */
    int expiredTokenByUsername(String username);

    /**
     * @param token
     * @return
     */
    Optional<AccessToken> findById(String token);

    /**
     * @param accessToken
     */
    AccessToken create(AccessToken accessToken);
}
