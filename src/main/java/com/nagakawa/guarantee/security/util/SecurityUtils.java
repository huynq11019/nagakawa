package com.nagakawa.guarantee.security.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.security.UserPrincipal;
import com.nagakawa.guarantee.util.StringPool;

/**
 * class for Spring Security.
 */
public final class SecurityUtils {

	public static List<GrantedAuthority> getAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	/**
	 * Get the JWT of the current user.
	 *
	 * @return the JWT of the current user.
	 */
	public static Optional<String> getCurrentUserJWT() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication())
				.filter(authentication -> authentication.getCredentials() instanceof String)
				.map(authentication -> (String) authentication.getCredentials());
	}

	/**
	 * Get the login of the current user.
	 *
	 * @return the login of the current user.
	 */
	public static Optional<String> getCurrentUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
			if (authentication.getPrincipal() instanceof UserDetails) {
				UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();

				return springSecurityUser.getUsername();
			} else if (authentication.getPrincipal() instanceof String) {
				return (String) authentication.getPrincipal();
			}

			return null;
		});
	}

	public static List<String> getNameAuthorities(Collection<GrantedAuthority> authorities) {
		return authorities.stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
	}

	public static List<String> getPrivileges() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		Optional<List<String>> privileges =
				Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
					if (authentication.getPrincipal() instanceof UserPrincipal) {
						UserPrincipal springSecurityUser =
								(UserPrincipal) authentication.getPrincipal();

						return getNameAuthorities(springSecurityUser.getAuthorities());
					}

					return null;
				});

		return privileges.orElse(new ArrayList<>());
	}

	public static Optional<User> getUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
			if (authentication.getPrincipal() instanceof UserPrincipal) {
				UserPrincipal springSecurityUser = (UserPrincipal) authentication.getPrincipal();

				return springSecurityUser.getUser();
			}

			return null;
		});
	}

	public static Optional<Long> getUserLoginId() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
			if (authentication.getPrincipal() instanceof UserPrincipal) {
				UserPrincipal springSecurityUser = (UserPrincipal) authentication.getPrincipal();

				return springSecurityUser.getUserId();
			}

			return null;
		});
	}

	public static Optional<UserDetails> geUserDetails() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication()).map(authentication -> {
			if (authentication.getPrincipal() instanceof UserPrincipal) {
				return (UserPrincipal) authentication.getPrincipal();
			}

			return null;
		});
	}

	public static boolean isAdministrator() {
		List<String> privileges = getPrivileges();

		return isAdministrator(privileges);
	}

	public static boolean isAdministrator(List<String> authorities) {
		return authorities.contains(SecurityConstants.SystemRole.SUPER_ADMIN);
	}

	public static boolean hasAdministratorPrivilege(List<String> authorities, String prefix) {
		return authorities.contains(
				prefix + StringPool.UNDERLINE + SecurityConstants.Privilege.ADMINISTRATOR);
	}

	/**
	 * Check if a user is authenticated.
	 *
	 * @return true if the user is authenticated, false otherwise.
	 */
	public static boolean isAuthenticated() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication())
				.map(authentication -> !authentication.getAuthorities().isEmpty()).orElse(false);
	}

	/**
	 * If the current user has a specific authority (security role).
	 * <p>
	 * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
	 *
	 * @param authority the authority to check.
	 * @return true if the current user has the authority, false otherwise.
	 */
	public static boolean isCurrentUserHasPrivilege(String authority) {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		return Optional.ofNullable(securityContext.getAuthentication())
				.map(authentication -> authentication.getAuthorities().stream().anyMatch(
						grantedAuthority -> grantedAuthority.getAuthority().equals(authority)))
				.orElse(false);
	}

	public static boolean isUser() {
		List<String> privileges = getPrivileges();

		return isUser(privileges);
	}

	public static boolean isUser(List<String> authorities) {
		return authorities.contains(SecurityConstants.SystemRole.USER);
	}
}
