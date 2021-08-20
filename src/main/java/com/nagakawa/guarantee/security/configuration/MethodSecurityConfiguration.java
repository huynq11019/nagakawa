/**
 * 
 */
package com.nagakawa.guarantee.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import com.nagakawa.guarantee.security.PermissionEvaluatorImpl;
import com.nagakawa.guarantee.security.handler.MethodSecurityExpressionHandlerImpl;

/**
 * @author LinhLH - ok
 *
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		// final DefaultMethodSecurityExpressionHandler expressionHandler = new
		// DefaultMethodSecurityExpressionHandler();
		final MethodSecurityExpressionHandlerImpl expressionHandler = new MethodSecurityExpressionHandlerImpl(
				new PermissionEvaluatorImpl());

		return expressionHandler;
	}
}
