/*
 * AccessTokenRepository.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.repository.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.nagakawa.guarantee.repository.extend.AccessTokenRepositoryExtend;

import lombok.extern.slf4j.Slf4j;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
public class AccessTokenRepositoryImpl implements AccessTokenRepositoryExtend {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int expiredTokenByUsername(String username) {
        int result = 0;
        
        try {
            StringBuilder sb = new StringBuilder(1);
            
            sb.append("update AccessToken set expired = false createdBy=: username ");
            
            Query query = entityManager.createQuery(sb.toString());
            
            query.setParameter("username", username);
            
            result = query.executeUpdate();
        } catch (Exception e) {
            _log.error("Error occurred when expired token by username", e);
        }
        
        return result;
    }
}
