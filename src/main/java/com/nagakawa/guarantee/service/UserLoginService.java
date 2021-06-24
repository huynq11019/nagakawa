/*
 * UserLoginService.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.service;

import com.nagakawa.guarantee.model.UserLogin;

/**
 * 23/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface UserLoginService {

    /**
     * @param loginLog
     */
    UserLogin save(UserLogin loginLog);
}
