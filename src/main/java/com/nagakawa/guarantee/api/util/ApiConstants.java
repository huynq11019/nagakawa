package com.nagakawa.guarantee.api.util;

import java.net.URI;

public final class ApiConstants {
	public interface EntityName {
		public static final String ACCOUNT = "account";

		public static final String CAPTCHA = "captcha";
		
		public static final String CONTENT_TEMPLATE = "content-template";

		public static final String DISTRICT = "district";

		public static final String OTP = "otp";

		public static final String PROVINCE = "province";

		public static final String SIGNATURE = "signature";

		public static final String WARD = "ward";

        public static final String USER = "user";

        public static final String ROLE = "role";
        
        public static final String FILE = "file";
	}
	
	public interface ErrorCode {
        public static final int FAILURE = 2;

        public static final int SUCCESS = 1;
    }
	
    public interface ErrorKey {
        public static final String FIELD_ERRORS = "fieldErrors";

        public static final String MESSAGE = "message";
        
        public static final String ERROR_KEY = "errorKey";
        
        public static final String PARAMS = "params";

        public static final String PATH = "path";

        public static final String VIOLATIONS = "violations";
    }

    public interface ErrorType {
        public static final String PROBLEM_BASE_URL = "http://www.nagakawa.vn/problem";

        public static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message");

        public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation");

        public static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password");

        public static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used");

        public static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used");

        public static final URI PHONE_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/phone-already-used");

        public static final URI APPOINTMENT_TIME_AVAILABLE_TYPE = URI
                .create(PROBLEM_BASE_URL + "/appointment-time-used");
    }

    public interface HttpHeaders {
        public static final String LINK_FORMAT = "<{0}>; rel=\"{1}\"";

        public static final String X_TRANSACTION_ID = "X-TRANSACTION-ID";

        public static final String X_SIGNATURE = "X-SIGNATURE";

        public static final String X_ACTION_MESSAGE = "X-Action-Message";

        public static final String X_ACTION_PARAMS = "X-Action-Params";
        
        public static final String X_FORWARDED_FOR = "X-Forwarded-For";

        public static final String X_TOTAL_COUNT = "X-Total-Count";
    }
    
    public interface Pagination {
        public static final String NEXT = "next";
        
        public static final String PREV = "prev";
        
        public static final String LAST = "last";
        
        public static final String FIRST = "first";
        
        public static final String PAGE = "page";
        
        public static final String SIZE = "size";
    }
}
