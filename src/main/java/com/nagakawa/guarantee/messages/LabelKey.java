/*
 * LabelKey.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.messages;

/**
 * 01/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface LabelKey {
    public static final String ERROR_CONCURRENCY_FAILURE = "error.concurrency-failure";
    
    public static final String ERROR_INVALID_REFRESH_TOKEN = "error.invalid-refresh-token";
    
    public static final String ERROR_INVALID_TOKEN = "error.invalid-token";
    
    public static final String ERROR_INVALID_USER_OR_PASSWORD = "error.invalid-user-or-password";
    
    public static final String ERROR_USER_COULD_NOT_BE_FOUND = "error.user-could-not-be-found";

    public static final String ERROR_METHOD_ARGUMENT_NOT_VALID = "error.method-argument-not-valid";

    public static final String SERVER_STARTED = "server.started";

    public static final String ERROR_CONSTRAINT_VIOLATION = "error.constraint-violation";
}
