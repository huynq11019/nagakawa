/*
 * AccessTokenRepositoryExtend.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.repository.extend;

/**
 * 31/05/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface AccessTokenRepositoryExtend {
    int expiredTokenByUsername(String username);
}
