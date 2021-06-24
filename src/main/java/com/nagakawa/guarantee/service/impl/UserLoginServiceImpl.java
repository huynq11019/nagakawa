/*
 * UserLoginServiceImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nagakawa.guarantee.model.UserLogin;
import com.nagakawa.guarantee.repository.UserLoginRepository;
import com.nagakawa.guarantee.service.UserLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 23/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository userLoginRepository;
    
    @Override
    public UserLogin save(UserLogin loginLog) {
        return userLoginRepository.save(loginLog);
    }

}
