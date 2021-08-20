/*
 * LabelKey.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved. This software is the confidential and
 * proprietary information of Evotek
 */
package com.nagakawa.guarantee.messages;

/**
 * 01/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
public interface LabelKey {

	public static final String ERROR_AN_UNEXPECTED_ERROR_HAS_OCCURRED = "error.an-unexpected-error-has-occurred";

	public static final String ERROR_AN_UNEXPECTED_ERROR_HAS_OCCURRED_WHEN_EXPORT =
			"error.an-unexpected-error-has-occurred-when-export";

	public static final String ERROR_APPROVE_WAS_NOT_SUCCESSFUL = "error.approve-was-not-successful";

	public static final String ERROR_BAD_REQUEST = "error.bad-request";

	public static final String ERROR_CANNOT_ASSIGN_ROLE_THAT_HAS_LOWER_LEVEL_THAN_YOURS =
			"error.cannot-assign-role-that-has-lower-level-than-yours";

	public static final String ERROR_CANNOT_CREATE_A_ROLE_THAT_HAS_LOWER_LEVEL_THAN_YOURS =
			"error.you-cannot-create-a-role-has-lower-level-roles-than-yours";

	public static final String ERROR_CANNOT_CREATE_DATA_WITH_EXISTED_ID = "error.cannot-create-data-with-existed-id";

	public static final String ERROR_CANNOT_EXPORT_TOO_MUCH_RECORD = "error.cannot-export-too-much-record";

	public static final String ERROR_CANNOT_OVERRIDE_DATA = "error.cannot-override-data";

	public static final String ERROR_CANNOT_UPDATE_THE_ROLE_THAT_HAS_LOWER_LEVEL_THAN_YOURS =
			"error.you-cannot-update-the-role-has-lower-level-roles-than-yours";

	public static final String ERROR_CONCURRENCY_FAILURE = "error.concurrency-failure";

	public static final String ERROR_CONSTRAINT_VIOLATION = "error.constraint-violation";

	public static final String ERROR_DATA_COULD_NOT_BE_FOUND = "error.data-could-not-be-found";

	public static final String ERROR_DATA_DOES_NOT_EXIST = "error.data-does-not-exist";

	public static final String ERROR_DATA_DOES_NOT_EXIST_OR_YOU_ARE_NOT_ALLOWED_TO_PERFORM_THIS_ACTION =
			"error.data-does-not-exist-or-you-are-not-allowed-to-perform-this-action";

	public static final String ERROR_DATA_DOES_NOT_EXIST_WITH_CODE = "error.data-does-not-exist-with-code";

	public static final String ERROR_DATA_DOES_NOT_EXIST_WITH_NAME = "error.data-does-not-exist-with-name";

	public static final String ERROR_DATA_IS_INCORRECT_WITH_NAME_OR_CODE = "error.data-is-incorrect-with-name-or-code";

	public static final String ERROR_DATE_CANNOT_BE_IN_THE_FUTURE = "error.date-cannot-be-in-the-future";

	public static final String ERROR_DATE_CANNOT_BE_IN_THE_PAST = "error.date-cannot-be-in-the-past";

	public static final String ERROR_DATE_INJECT_REGISTRATION_CANNOT_LESS_THAN_NOW =
			"error.date-inject-registration-cannot-less-than-now";

	public static final String ERROR_DATE_OF_BIRTH_CANNOT_GREATER_THAN_NOW =
			"error.date-of-birth-cannot-greater-than-now";

	public static final String ERROR_DELETED_WAS_NOT_SUCCESSFUL = "error.deleted-was-not-successful";

	public static final String ERROR_DUPLICATE_DATA = "error.duplicate-data";

	public static final String ERROR_EXCEED_MAX_LENGTH = "error.exceed-max-length";

	public static final String ERROR_INCORRECT_CAPTCHA = "error.incorrect-captcha";

	public static final String ERROR_INCORRECT_OTP = "error.incorrect-otp";

	public static final String ERROR_INCORRECT_SIGNATURE = "error.incorrect-signature";

	public static final String ERROR_INPUT_CANNOT_BE_EMPTY = "error.input-cannot-be-empty";

	public static final String ERROR_INPUT_INVALID_LENGTH = "error.input-invalid-length";

	public static final String ERROR_INPUT_INVALID_MAX_LENGTH = "error.input-invalid-max-length";

	public static final String ERROR_INPUT_MUST_BE_ASSIGNED = "error.input-must-be-assigned";

	public static final String ERROR_INPUT_MUST_BE_NON_NEGATIVE = "error.input-must-be-non-negative";

	public static final String ERROR_INPUT_MUST_BE_POSITIVE = "error.input-must-be-positive";

	public static final String ERROR_INVALID = "error.invalid";

	public static final String ERROR_INVALID_AREA = "error.invalid-area";

	public static final String ERROR_INVALID_DATA = "error.invalid-data";

	public static final String ERROR_INVALID_DATA_FORMAT = "error.invalid-data-format";

	public static final String ERROR_INVALID_EXCEL_TEMPLATE = "error.invalid-excel-template";

	public static final String ERROR_INVALID_INPUT_DATA = "error.invalid-input-data";

	public static final String ERROR_INVALID_REFRESH_TOKEN = "error.invalid-refresh-token";

	public static final String ERROR_INVALID_TOKEN = "error.invalid-token";

	public static final String ERROR_INVALID_USERNAME = "error.invalid-username";

	public static final String ERROR_INVALID_USERNAME_OR_PASSWORD = "error.invalid-username-or-password";

	public static final String ERROR_LIMIT_OTP_ATTEMPTS = "error.limit-otp-attempts";

	public static final String ERROR_LOCATION_OF_CHILDREN_MUST_BE_THE_SAME_AS_THE_PARENT =
			"error.location-of-children-must-be-the-same-as-the-parent";

	public static final String ERROR_MAXIMUM_NUMBER_OF_OTP_REACHED = "error.maximum-number-of-otp-reached";

	public static final String ERROR_METHOD_ARGUMENT_NOT_VALID = "error.method-argument-not-valid";

	public static final String ERROR_MORE_THAN_ONE_DATA_WITH_THE_GIVEN_NAME_WAS_FOUND =
			"error.more-than-one-data-with-the-given-name-was-found";

	public static final String ERROR_NEW_PASSWORD_CAN_NOT_BE_THE_SAME_AS_OLD_PASSWORD =
			"error.new-password-can-not-be-the-same-as-old-password";

	public static final String ERROR_NO_DATA_TO_IMPORT = "error.no-data-to-import";

	public static final String ERROR_OTP_HAS_EXPIRED = "error.otp-has-expired";

	public static final String ERROR_OTP_IS_INCORRECT_OR_HAS_EXPIRED = "error.otp-is-incorrect-or-has-expired";

	public static final String ERROR_OTP_IS_MISSING = "error.otp-is-missing";

	public static final String ERROR_OTP_IS_NOT_SENT = "error.otp-is-not-sent";

	public static final String ERROR_SERVICE_UNAVAILABLE = "error.service-unavailable";

	public static final String ERROR_SOME_DATA_ARE_MISSING = "error.some-data-are-missing";

	public static final String ERROR_THE_FILE_DOES_NOT_EXIST = "error.the-file-does-not-exist";

	public static final String ERROR_THE_PASSWORD_CONFIRMATION_DOES_NOT_MATCH =
			"error.the-password-confirmation-does-not-match";

	public static final String ERROR_USER_COULD_NOT_BE_FOUND = "error.user-could-not-be-found";

	public static final String ERROR_USER_IS_NOT_LOGGED_IN = "error.user-is-not-logged-in";

	public static final String ERROR_YOU_CANNOT_LOCK_YOURSELF = "error.you-cannot-lock-yourself";

	public static final String ERROR_YOU_CANNOT_UPDATE_THE_USER_HAS_LOWER_LEVEL_ROLES_THAN_YOURS =
			"error.you-cannot-update-the-user-has-lower-level-roles-than-yours";

	public static final String ERROR_YOU_DO_NOT_HAVE_PERMISSION_TO_ASSIGN_THIS_DISTRICT =
			"error.you-do-not-have-permission-to-assign-this-district";

	public static final String ERROR_YOU_DO_NOT_HAVE_PERMISSION_TO_ASSIGN_THIS_PROVINCE =
			"error.you-do-not-have-permission-to-assign-this-province";

	public static final String ERROR_YOU_DO_NOT_HAVE_PERMISSION_TO_ASSIGN_THIS_WARD =
			"error.you-do-not-have-permission-to-assign-this-ward";

	public static final String ERROR_YOU_MIGHT_NOT_HAVE_PERMISSION_TO_ASSIGN_THIS_DATA =
			"error.you-might-not-have-permission-to-assign-this-data";

	public static final String ERROR_YOU_MIGHT_NOT_HAVE_PERMISSION_TO_PERFORM_THIS_ACTION =
			"error.you-might-not-have-permission-to-perform-this-action";

	public static final String ERROR_YOU_MUST_ENTER_A_VALUE_FOR_ALL_REQUIRED_FIELDS =
			"error.you-must-enter-a-value-for-all-required-fields";

	public static final String ERROR_YOU_MUST_ENTER_A_VALUE_FOR_REQUIRED_FIELD =
			"error.you-must-enter-a-value-for-required-field";

	public static final String ERROR_YOU_ONLY_CAN_PERFORM_THIS_ACTION_ON_DATA_THAT_HAS_STATUS =
			"error.you-only-can-perform-this-action-on-data-that-has-status";

	public static final String ERROR_YOU_ONLY_CAN_PERFORM_THIS_ACTION_ON_THE_DATA_THAT_YOU_HAVE_CREATED =
			"error.you-only-can-perform-this-action-on-the-data-that-you-have-created";

	public static final String ERROR_YOUR_CONFIRM_PASSWORD_IS_NOT_MATCH = "error.your-confirm-password-is-not-match";

	public static final String ERROR_YOUR_CURRENT_PASSWORD_IS_INCORRECT = "error.your-current-password-is-incorrect";

	public static final String ERROR_YOUR_CURRENT_PASSWORD_IS_MISSING = "error.your-current-password-is-missing";

	public static final String ERROR_YOUR_CURRENT_PASSWORD_IS_MISSING_OR_INCORRECT =
			"error.your-current-password-is-missing-or-incorrect";

	public static final String ERROR_YOUR_NEW_PASSWORD_IS_MISSING = "error.your-new-password-is-missing";

	public static final String ERROR_YOUR_NEW_PASSWORD_IS_MISSING_OR_CONFIRM_PASSWORD_NOT_MATCH =
			"error.your-new-password-is-missing-or-confirm-password-not-match";

	public static final String ERROR_YOUR_REGISTRATION_WAS_NOT_SUCCESSFUL =
			"error.your-registration-was-not-successful";

	public static final String LABEL_ADDRESS = "label.address";

	public static final String LABEL_CONFIRM_PASSWORD = "label.confirm-password";

	public static final String LABEL_CONTACT_PHONE_NUMBER = "label.contact-phone-number";

	public static final String LABEL_COORDINATE = "label.coordinate";

	public static final String LABEL_DATE_OF_BIRTH = "label.date-of-birth";

	public static final String LABEL_DISTRICT = "label.district";

	public static final String LABEL_EMAIL = "label.email";

	public static final String LABEL_FILE = "label.file";

	public static final String LABEL_FULLNAME = "label.fullname";

	public static final String LABEL_GENDER = "label.gender";

	public static final String LABEL_HEADACHE = "label.headache";

	public static final String LABEL_IDENTIFICATION = "label.identification";

	public static final String LABEL_JOB = "label.job";

	public static final String LABEL_LEVEL = "label.level";

	public static final String LABEL_NATION = "label.nation";

	public static final String LABEL_NOTE = "label.note";

	public static final String LABEL_PASSWORD = "label.password";

	public static final String LABEL_PHONE_NUMBER = "label.phone-number";

	public static final String LABEL_PROVINCE = "label.province";

	public static final String LABEL_ROLE = "label.role";

	public static final String LABEL_ROLE_NAME = "label.role-name";

	public static final String LABEL_STATUS = "label.status";

	public static final String LABEL_STATUS_DRAFT = "label.status-draf";

	public static final String LABEL_STATUS_NOT_SENT = "label.status-not-sent";

	public static final String LABEL_USER = "label.user";

	public static final String LABEL_USERNAME = "label.username";

	public static final String LABEL_WARD = "label.ward";

	public static final String MESSAGE_FIRST = "message.first";

	public static final String MESSAGE_LOGIN_SUCCESSFUL = "message.login-successful";

	public static final String MESSAGE_OTP_HAS_BEEN_SENT_TO_YOUR_EMAIL_ADDRESS =
			"message.otp-has-been-sent-to-your-email-address";

	public static final String MESSAGE_OTP_HAS_BEEN_SENT_TO_YOUR_MOBILE_NUMBER =
			"message.otp-has-been-sent-to-your-mobile-number";

	public static final String MESSAGE_REQUEST_FOR_APPROVAL_WAS_SUCCESSFUL =
			"message.request-for-approval-was-successful";

	public static final String MESSAGE_SECOND = "message.second";

	public static final String MESSAGE_SERVER_HAS_BEEN_STARTED = "message.server-has-been-started";

	public static final String MESSAGE_SUCCESSFUL = "message.successful";

	public static final String MESSAGE_THIRD = "message.third";

	public static final String MESSAGE_YOU_HAVE_SUCCESSFULLY_CHANGED_PASSWORD =
			"message.you-have-successfully-changed-password";

	public static final String MESSAGE_YOU_HAVE_SUCCESSFULLY_REGISTERED_FOR_AN_ACCOUNT =
			"message.you-have-successfully-registered-for-an-account";

	public static final String MESSAGE_YOU_HAVE_SUCCESSFULLY_VERIFIED_YOUR_ACCOUNT =
			"message.you-have-successfully-verified-your-account";
}
