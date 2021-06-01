/**
 * 
 */
package com.nagakawa.guarantee.security;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.nagakawa.guarantee.model.User;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LinhLH
 *
 */
@Getter
@Setter
public class UserPrincipal extends org.springframework.security.core.userdetails.User implements Serializable {
	private static final long serialVersionUID = 6960173949433045836L;
	
	private User user;

	private Collection<String> roles;
	
	private String hashKey;
	/**
	 * @param username
	 * @param password
	 * @param authorities
	 * @param user
	 * @param healthFacilities
	 */
	public UserPrincipal(User user, Collection<String> roles, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		
		this.user = user;

		this.roles = roles;
	}
}
