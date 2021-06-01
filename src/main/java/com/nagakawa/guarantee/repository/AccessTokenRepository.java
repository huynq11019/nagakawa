/*
 * AccessTokenRepository.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.repository;

import java.time.Instant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagakawa.guarantee.model.AccessToken;
import com.nagakawa.guarantee.repository.extend.AccessTokenRepositoryExtend;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, String>, AccessTokenRepositoryExtend{
    long deleteByExpiredDateLessThanEqualOrExpired(Instant expiredInstant, boolean expired);
}
