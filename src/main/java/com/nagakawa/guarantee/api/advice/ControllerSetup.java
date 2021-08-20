/**
 * 
 */
package com.nagakawa.guarantee.api.advice;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author LinhLH
 * 
 *         Trim all String parameter from api request
 *
 */
@ControllerAdvice
public class ControllerSetup {
	@InitBinder
	public void init(WebDataBinder dataBinder) {
		dataBinder.registerCustomEditor(String[].class, new StringTrimmerEditor(true));
		dataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
