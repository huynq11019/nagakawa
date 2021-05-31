package com.nagakawa.guarantee.security.util;

/**
 * Constants for Spring Security authorities.
 */
public interface SecurityConstants {

	public interface SystemRole {
		public static final String ADMIN = "ADMIN";

		public static final String USER = "USER";

		public static final String ANONYMOUS = "ANONYMOUS";

		public static final String SUPER_ADMIN = "SUPER_ADMIN";
	}

	public interface Account {
		public static final String SYSTEM = "system";
	}

	public interface Jwt {
	    public static final String AUTHORIZATION_HEADER = "Authorization";
	    
		public static final String TOKEN_START = "Bearer ";

		public static final String PRIVILEGES = "privileges";

		public static final String HASHKEY = "hash-key";
		
		public static final String REFRESH_TOKEN = "refresh-token";
		
		public static final String USER_DETAIL = "user-detail";
	}
}
