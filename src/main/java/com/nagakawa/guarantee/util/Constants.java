package com.nagakawa.guarantee.util;

public interface Constants {
	public interface MEDICAL_STAFF_TYPE_ID {
		Long INJECTION_EXECUTOR = 1L; // injectionExecutor

		Long EMERGENCY_EXECUTOR = 2L;
	}

	public interface Regex {
		public static final String ACCENTED_SPACE =
				"^[_a-zA-Z0-9-ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W]*$";

		public static final String CODE = "[_a-zA-Z0-9]*";

		public static final String EMAIL =
				"^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,35})$";

		public static final String HOUR = "([01]?[0-9]|2[0-3])";

		public static final String IPV4 = "\\b" + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\."
				+ "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\." + "((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])"
				+ "\\b";

		public static final String IPV6 = "(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:"
				+ "|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}"
				+ "|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}"
				+ "|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})"
				+ "|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}"
				+ "|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\.){3,3}(25[0-5]|(2[0-4]"
				+ "|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\\."
				+ "){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))";

		public static final String MINUTE = "[0-5][0-9]";

		public static final String NUMBER = "[+-]?\\d*(\\.\\d+)?";

		public static final String PHONE_REGEX = "^((\\s){0,}((\\+84-?)|0))((9|8|7|3|5|4|2)[0-9]{8,9}(\\s){0,})$";

		public static final String PORT =
				"^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$";

		public static final String USERNAME = "^[a-zA-Z0-9]([._](?![._])|[a-zA-Z0-9]){1,72}[a-zA-Z0-9]$";

		public static final String VERSION = "^(\\d+\\.){1,3}(\\d+)";
	}

	public interface EntityStatus {
		public static final int DRAFT = -2;

		public static final int DELETED = -1, NOT_ACTIVE = -1;

		public static final int DEACTIVATE = 0, PENDING = 0, NEW = 0, NOT_LOCKED = 0;

		public static final int ACTIVE = 1, APPROVED = 1, WARD_LOCKED = 1;

		public static final int DENIED = 2, DISTRICT_LOCKED = 2;
		
		public static final int PROVINCE_LOCKED = 3;

	}

	public interface RegistrationType {
		public static final int PERSONAL = 1;

		public static final int ORGANIZATION = 2;
	}

	public interface SMSStatus {
		public static final int SUCCESS = 1;

		public static final int ERROR = 0;
	}

	public interface ContentTemplate {
		public static final String FORGOT_PWD = "FORGOT_PWD";

		public static final String VERIFY_ORG_ACCOUNT = "VERIFY_ORG_ACCOUNT";
	}

	int MAX_EXPORT_RECORD = 100000;

	int MAX_QUERY_RECORD = 3000;

	int MAX_QUERY_COMPLEX_RECORD = 50;

	long DEFAULT_MEDICAL_SUMMARY = 0L;

	int QUALIFY_SIGN = 1;

}
