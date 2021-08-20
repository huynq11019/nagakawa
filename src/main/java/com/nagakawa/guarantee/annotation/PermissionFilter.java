/*
 * HealthFacilityFilter.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and
 * proprietary information of Evotek
 */
package com.nagakawa.guarantee.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.Mapping;

/**
 * 08/07/2021 - LinhLH: Create new
 *
 * @author LinhLH Kiểm tra bản ghi có phải do user tạo ra không
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface PermissionFilter {
	PermissionLevel level();

	public Class<?> className() default void.class;

	// cho phep
	public boolean allowUnderLevel() default false;
}
