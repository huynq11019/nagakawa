/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.nagakawa.guarantee.security.util;

import java.util.Collection;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.nagakawa.guarantee.model.Role;

/**
 *
 * @author linhlh2
 */
public class PermissionUtil {
	public static String encodePassword(String password) {

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(11);

		return bCryptPasswordEncoder.encode(password);
	}

	public static boolean isAdministrator(Collection<String> roles) {
		return roles.contains(SecurityConstants.SystemRole.SUPER_ADMIN);
	}

	public static boolean isAdministrator(Role role) {
		return SecurityConstants.SystemRole.SUPER_ADMIN.equals(role.getName());
	}

	public static void main(String[] args) {
		System.err.println(PermissionUtil.encodePassword("12345678aA@"));
	}
}
