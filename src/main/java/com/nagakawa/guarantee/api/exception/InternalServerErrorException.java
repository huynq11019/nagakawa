package com.nagakawa.guarantee.api.exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import com.nagakawa.guarantee.api.util.ApiConstants;
import lombok.Getter;

@Getter
public class InternalServerErrorException extends AbstractThrowableProblem {

	private static final long serialVersionUID = 1L;

	private final String entityName;

	private final String errorKey;

	public InternalServerErrorException(String defaultMessage, String entityName, String errorKey) {
		this(ApiConstants.ErrorType.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
	}

	public InternalServerErrorException(String defaultMessage, String entityName, String errorKey,
                                        Map<String, Object> params, Map<String, Object> values) {
		super(ApiConstants.ErrorType.DEFAULT_TYPE, defaultMessage, Status.INTERNAL_SERVER_ERROR, null, null, null, params);

		params.put(ApiConstants.ErrorKey.MESSAGE, defaultMessage);
		params.put(ApiConstants.ErrorKey.ERROR_KEY, errorKey);

		if (values != null && !values.isEmpty()) {
			params.put(ApiConstants.ErrorKey.PARAMS, values);
		}

		this.entityName = entityName;
		this.errorKey = errorKey;
	}

	public InternalServerErrorException(String defaultMessage, String entityName, String errorKey,
                                        Map<String, Object> params) {
		super(ApiConstants.ErrorType.DEFAULT_TYPE, defaultMessage, Status.INTERNAL_SERVER_ERROR, null, null, null, params);

		params.put(ApiConstants.ErrorKey.MESSAGE, defaultMessage);
		params.put(ApiConstants.ErrorKey.ERROR_KEY, errorKey);

		this.entityName = entityName;
		this.errorKey = errorKey;
	}

    public InternalServerErrorException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.INTERNAL_SERVER_ERROR, null, null, null,
                getAlertParameters(defaultMessage, entityName, errorKey));

        this.entityName = entityName;
        this.errorKey = errorKey;
    }

	private static Map<String, Object> getAlertParameters(String message, String entityName, String errorKey) {
		Map<String, Object> parameters = new HashMap<>();
		
		parameters.put(ApiConstants.ErrorKey.MESSAGE, message);
		parameters.put(ApiConstants.ErrorKey.ERROR_KEY, errorKey);
		parameters.put(ApiConstants.ErrorKey.PARAMS, entityName);
		
		return parameters;
	}

}
