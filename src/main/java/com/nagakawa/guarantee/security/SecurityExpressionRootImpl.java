/*
 * SecurityExpressionRootImpl.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import com.nagakawa.guarantee.security.util.SecurityConstants;
import com.nagakawa.guarantee.util.StringPool;

/**
 * 06/07/2021 - LinhLH: Create new
 *
 * @author LinhLH - ok
 */
public class SecurityExpressionRootImpl extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
	private final PermissionEvaluator permissionEvaluator;
	
    private final Authentication authentication;
    
	private RoleHierarchy roleHierarchy;
	private Set<String> roles;
	private String defaultRolePrefix = "";

	private Object filterObject;
	private Object returnObject;
	private Object target;

	public SecurityExpressionRootImpl(Authentication authentication, PermissionEvaluator permissionEvaluator) {
		super(authentication);
		this.authentication = authentication;
		this.permissionEvaluator = permissionEvaluator;
		super.setPermissionEvaluator(permissionEvaluator);
	}

	public final boolean isAdministrator() {
		return hasRole(SecurityConstants.SystemRole.SUPER_ADMIN);
	}

	public boolean hasPrivilege(String privilege) {
		return isAdministrator() || hasAdministratorPrivilege(privilege) || hasAuthority(privilege);
	}
	
	public boolean hasAnyPrivilege(String... privileges) {
		return isAdministrator() || hasAnyAuthority(privileges);
	}
	
	public boolean hasAdministratorPrivilege(String privilege) {
		String[] parts = StringUtils.split(privilege, StringPool.UNDERLINE);

		if (parts.length <= 0) {
			return false;
		}

		return hasAuthority(
				parts[0] + StringPool.UNDERLINE + SecurityConstants.Privilege.ADMINISTRATOR);
	}
	
	@Override
	public Object getPrincipal() {
		return authentication.getPrincipal();
	}

	@Override
	public void setRoleHierarchy(RoleHierarchy roleHierarchy) {
		this.roleHierarchy = roleHierarchy;
	}

	@Override
	public void setDefaultRolePrefix(String defaultRolePrefix) {
		this.defaultRolePrefix = defaultRolePrefix;
	}

	private Set<String> getAuthoritySet() {
		if (roles == null) {
			Collection<? extends GrantedAuthority> userAuthorities = authentication.getAuthorities();

			if (roleHierarchy != null) {
				userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
			}

			roles = AuthorityUtils.authorityListToSet(userAuthorities);
		}

		return roles;
	}

	@Override
	public boolean hasPermission(Object target, Object permission) {
		return permissionEvaluator.hasPermission(authentication, target, permission);
	}

	@Override
	public boolean hasPermission(Object targetId, String targetType, Object permission) {
		return permissionEvaluator.hasPermission(authentication, (Serializable) targetId, targetType, permission);
	}

	private static String getRoleWithDefaultPrefix(String defaultRolePrefix, String role) {
		if (role == null) {
			return role;
		}
		if ((defaultRolePrefix == null) || (defaultRolePrefix.length() == 0)) {
			return role;
		}
		if (role.startsWith(defaultRolePrefix)) {
			return role;
		}
		return defaultRolePrefix + role;
	}

	@Override
	public Object getFilterObject() {
		return this.filterObject;
	}

	@Override
	public Object getReturnObject() {
		return this.returnObject;
	}

	@Override
	public Object getThis() {
		return this.target;
	}

	@Override
	public void setFilterObject(Object obj) {
		this.filterObject = obj;
	}

	@Override
	public void setReturnObject(Object obj) {
		this.returnObject = obj;
	}
}
