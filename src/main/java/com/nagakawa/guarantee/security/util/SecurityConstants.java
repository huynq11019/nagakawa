package com.nagakawa.guarantee.security.util;

/**
 * Constants for Spring Security authorities.
 */
public interface SecurityConstants {

	public interface SystemRole {
	    public static final String ADMIN = "ROLE_ADMIN";

	    public static final String USER = "ROLE_USER";

	    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

	    public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
	}

	public interface Account {
		public static final String SYSTEM = "system";
	}
	
	public interface JwtClaimKey {
		public static final String PRIVILEGES = "privileges";
	}
}
