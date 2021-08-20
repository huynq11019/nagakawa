/*
 * HttpUtil.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.api.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 07/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public class HttpUtil {
	public static String getClientIP(HttpServletRequest request) {
		String xfHeader = request.getHeader(ApiConstants.HttpHeaders.X_FORWARDED_FOR);
		
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    
	    return xfHeader.split(",")[0];
	}
}
