/*
 * AccessTokenServiceImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service.impl;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagakawa.guarantee.model.AccessToken;
import com.nagakawa.guarantee.repository.AccessTokenRepository;
import com.nagakawa.guarantee.service.AccessTokenService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {    
    private final AccessTokenRepository accessTokenRepository;

    @Override
    public long deleteAllExpired() {
        return accessTokenRepository.deleteByExpiredDateLessThanEqualOrExpired(Instant.now(), true);
    }

    @Override
    public int expiredTokenByUsername(String username) {
        return accessTokenRepository.expiredTokenByUsername(username);
    }

    @Override
    public Optional<AccessToken> findById(String token) {
        return accessTokenRepository.findById(token);
    }

    @Override
    public AccessToken create(AccessToken accessToken) {
        //invalidate all token by username
        this.expiredTokenByUsername(accessToken.getUsername());
        
        //save new token
        return accessTokenRepository.save(accessToken);
    }
}
