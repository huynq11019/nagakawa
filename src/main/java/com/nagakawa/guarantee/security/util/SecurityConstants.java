package com.nagakawa.guarantee.security.util;

/**
 * Constants for Spring Security authorities.
 */
public interface SecurityConstants {

	public interface SystemRole {
		public static final String USER = "ROLE_USER";

		public static final String ANONYMOUS = "ROLE_ANONYMOUS";

		public static final String SUPER_ADMIN = "ROLE_SUPER_ADMIN";

	}

	public interface Privilege {
		public static final String ADMINISTRATOR = "ADMINISTRATOR";

		public static final String APPROVAL = "APPROVAL";

		public static final String APPROVAL_REQUEST = "APPROVAL_REQUEST";

		public static final String CREATE = "CREATE";

		public static final String DELETE = "DELETE";

		public static final String EXPORT = "EXPORT";

		public static final String IMPORT = "IMPORT";

		public static final String READ = "READ";

		public static final String WRITE = "WRITE";
	}
	
	public interface Account {
		public static final String SUPER_AMDIN = "superadmin";
	}

	public interface Header {
	    public static final String AUTHORIZATION_HEADER = "Authorization";
	    
		public static final String TOKEN_START = "Bearer ";

		public static final String PRIVILEGES = "privileges";

		public static final String HASHKEY = "hash-key";
		
		public static final String LOCALE = "locale";
		
		public static final String REFRESH_TOKEN = "refresh-token";
		
		public static final String USER = "user";
	}
	
	public interface Cookie {
		public static final String ACCESS_TOKEN = "vr-accessToken";

		public static final String REFRESH_TOKEN = "vr-refreshToken";
		
		public static final String REMEMBER_ME = "vr-rememberMe";
	}
}
