package com.nagakawa.guarantee.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import com.nagakawa.guarantee.model.Privilege;
import com.nagakawa.guarantee.model.Role;
import com.nagakawa.guarantee.model.User;
import com.nagakawa.guarantee.repository.UserRepository;
import com.nagakawa.guarantee.security.exception.UserNotActivatedException;
import com.nagakawa.guarantee.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
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
				_log.error("User not exist with name :" + username);
				
				return null;
			}

		} catch (Exception e) {
			_log.error("Cannot loadUserByUsername userName:" + username + " Exception: " + e.getMessage(), e);
		}

		// Create the UserDetails object for a specified user with
		// their grantedAuthorities List.
		final UserDetails userDetails = this.createSpringSecurityUser(username, user);

		if (_log.isDebugEnabled()) {
			_log.debug("Rights for '" + user.getUsername() + "' (ID: " + user.getId() + ") evaluated. [" + this + "]");
		}

		return userDetails;
	}
	
	private UserPrincipal createSpringSecurityUser(String username, User user) {
        if (user.getStatus() != Constants.EntityStatus.ACTIVE) {
            throw new UserNotActivatedException("User " + username + " was not activated");
        }

        List<Role> roles = user.getRoles();

        List<String> roleNames = new ArrayList<>();

        List<Privilege> privileges = new ArrayList<>();

        roles.forEach((role) -> {
            if (role.getStatus() == Constants.EntityStatus.ACTIVE) {
                roleNames.add(role.getName());

                privileges.addAll(role.getPrivileges());
            }
        });

        List<GrantedAuthority> grantedAuthorities = privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.getName())).collect(Collectors.toList());

        // add role name to authority list
        roleNames.stream().forEach(roleName -> grantedAuthorities.add(new SimpleGrantedAuthority(roleName)));

        final UserPrincipal userPrincipal = new UserPrincipal(user, roleNames, grantedAuthorities);

        return userPrincipal;
    }
}
