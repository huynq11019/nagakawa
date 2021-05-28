package com.nagakawa.guarantee.configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[._A-Za-z0-9-]*$";
    public static final String ACCENTED_SPACE_REGEX = "^[_a-zA-Z0-9-ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s\\W]*$";
    public static final String EMAIL_REGEX = "^(\\s){0,}[a-zA-Z][a-zA-Z0-9_\\.]{2,32}@[a-zA-Z0-9]{2,}(\\.[a-zA-Z0-9]{2,4}){1,2}(\\s){0,}$";
    public static final String PHONE_REGEX = "^((\\s){0,}((\\+84-?)|0))((9|8|7|3|5|4|2)[0-9]{8,9}(\\s){0,})$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String DEFAULT_LANGUAGE = "vi";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String TEMP_DIR = "temp";
    public static final String ROOT_FOLDER_FTP = "uploads/";
    public static final String SLASH = "/";
    public static final String UNDER_SCORE = "_";
    public static final String DOUBLE_SLASH = "//";
    public static final String DOT = ".";
    private final static List<String> EXTENSIONS = Arrays.asList(("bmp,jpg,png,jpeg".split(",")));
    private final static List<String> SIZES = Arrays.asList(("350x233,255x170,255x212,350x194,160x80,60x60,316x253".split(",")));
    public static final String PARENT_CODE_CATEGORY = "PROJECT";
    public static final String TYPE_PLACE = "PLACE";
    public static final String DATAPROCESSING = "dataprocessing";


    public static List<String> getValidExtensions() {
        return EXTENSIONS;
    }

    private Constants() {
    }

    public static interface USER_STATE {
        Integer ACTIVATED = 1;
        Integer UNACTIVATED = 0;
    }

    public static interface STATE {
        Integer ACTIVATED = 1;
        Integer UNACTIVATED = 0;
    }
}
