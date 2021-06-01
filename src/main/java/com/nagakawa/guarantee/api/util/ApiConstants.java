package com.nagakawa.guarantee.api.util;

import java.net.URI;

public final class ApiConstants {
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
        public static final String X_TOTAL_COUNT = "X-Total-Count";
        
        public static final String X_ACTION_PARAMS = "X-Action-Params";
        
        public static final String X_ACTION_MESAGE = "X-Action-Mesage";
        
        public static final String LINK_FORMAT = "<{0}>; rel=\"{1}\"";
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
