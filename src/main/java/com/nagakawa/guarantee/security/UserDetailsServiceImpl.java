package com.nagakawa.guarantee.security;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.nagakawa.guarantee.model.Privilege;
import com.nagakawa.guarantee.model.Role;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.model.UserLogin;
import com.nagakawa.guarantee.repository.UserLoginRepository;
import com.nagakawa.guarantee.repository.UserRepository;
import com.nagakawa.guarantee.util.Constants;
import com.nagakawa.guarantee.util.Validator;

import lombok.RequiredArgsConstructor;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final Logger _log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	private final UserRepository userRepository;

	private final UserLoginRepository userLoginRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) {
		_log.info("Authenticating {}", username);

		User user = null;

		try {
			user = new EmailValidator().isValid(username, null) ? this.userRepository
					.findOneWithRolesByEmailIgnoreCaseAndStatusIs(username, Constants.EntityStatus.ACTIVE).orElse(null)
					: this.userRepository
							.findOneWithRolesByUsernameIgnoreCaseAndStatusIs(username, Constants.EntityStatus.ACTIVE)
							.orElse(null);

			if (user == null) {
				this.saveLoginFailure(username);

				throw new UsernameNotFoundException("User not exist with name :" + username);
			}

		} catch (Exception e) {
			_log.error("Cannot loadUserByUsername userName:" + username + " Exception: " + e.getMessage(), e);
		}

		// Create the UserDetails object for a specified user with
		// their grantedAuthorities List.
		final UserDetails userDetails = createSpringSecurityUser(username, user);

		if (_log.isDebugEnabled()) {
			_log.debug("Rights for '" + user.getUsername() + "' (ID: " + user.getId() + ") evaluated. [" + this + "]");
		}

		return userDetails;
	}

	private UserPrincipal createSpringSecurityUser(String username, User user) {
		if (user.getStatus() != Constants.EntityStatus.ACTIVE) {
			throw new UserNotActivatedException("User " + username + " was not activated");
		}

		Set<Role> roles = user.getRoles();

		List<String> roleNames = new ArrayList<>();

		List<Privilege> privileges = new ArrayList<>();

		roles.forEach((role) -> {
			roleNames.add(role.getName());

			privileges.addAll(role.getPrivileges());
		});

		List<GrantedAuthority> grantedAuthorities = privileges.stream()
				.map(privilege -> new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toList());

		final UserPrincipal userPrincipal = new UserPrincipal(user, roleNames, grantedAuthorities);

		return userPrincipal;
	}
	
	private void saveLoginFailure(String username) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();

		String ip = request.getRemoteAddr();

		if (Validator.isIPAddress(ip)) {
			UserLogin loginLog = UserLogin.builder()
					.username(username)
					.ip(ip)
					.loginTime(Instant.now())
					.success(false)
					.build();

			this.userLoginRepository.save(loginLog);
		}
	}
}
