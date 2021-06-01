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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nagakawa.guarantee.repository.extend.AccessTokenRepositoryExtend;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public class AccessTokenRepositoryImpl implements AccessTokenRepositoryExtend {
    @PersistenceContext
    private EntityManager entityManager;

    private final Logger _log = LoggerFactory.getLogger(AccessTokenRepositoryImpl.class);

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
