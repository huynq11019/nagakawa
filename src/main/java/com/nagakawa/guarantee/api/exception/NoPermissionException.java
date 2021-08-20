/*
 * NoPermissionException.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.api.exception;

import com.nagakawa.guarantee.messages.LabelKey;
import com.nagakawa.guarantee.messages.Labels;

/**
 * 06/07/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public class NoPermissionException extends RuntimeException {

	/** The Constant serialVersionUID */
	private static final long serialVersionUID = -4462714642740568013L;

	public NoPermissionException() {
		super(Labels.getLabels(LabelKey.ERROR_YOU_MIGHT_NOT_HAVE_PERMISSION_TO_PERFORM_THIS_ACTION));
	}

	public NoPermissionException(String message) {
		super(message);
	}

	public NoPermissionException(String message, Throwable t) {
		super(message, t);
	}
}
