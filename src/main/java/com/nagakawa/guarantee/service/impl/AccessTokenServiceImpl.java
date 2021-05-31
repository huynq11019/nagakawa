/*
 * AccessTokenServiceImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service.impl;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagakawa.guarantee.repository.AccessTokenRepository;
import com.nagakawa.guarantee.service.AccessTokenService;

import lombok.RequiredArgsConstructor;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AccessTokenServiceImpl implements AccessTokenService {
    private final static Logger _log = LoggerFactory.getLogger(AccessTokenServiceImpl.class);
    
    private final AccessTokenRepository accessTokenRepository;

    @Override
    public long deleteAllExpired() {
        return accessTokenRepository.deleteByExpiredDateLessThanEqualOrExpired(Instant.now(), true);
    }
}
