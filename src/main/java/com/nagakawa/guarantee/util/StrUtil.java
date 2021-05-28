package com.nagakawa.guarantee.util;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.util.MultiValueMap;

public class StrUtil {
    public static final String DOT = ".";
    public static final String SLASH = "/";
    public static final String ROOT = "root";
    public static final String EMPTY = "";
    public static final String AREA_SPLIT = "-tai-";
    public static final String AREA__ACCENT_SPLIT = " tại ";
    public static final String STAR = "*";
    public static final String SPACE = " ";
    public static final char CHAR_SHACKLE = '@';
    public static final char COMMA = ',';
    public static final Integer CHAR_REPLACE_START = 3;

    private static final Pair<String, String> LIKE_ESCAPE = Pair.of("(_|%)", "\\\\$1");

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isBlank(Object str) {
        return str == null || ((String) str).trim().length() == 0;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static String getGeneralField(String getOrSetMethodName) {
        if (getOrSetMethodName.startsWith("get") || getOrSetMethodName.startsWith("set")) {
            return cutPreAndLowerFirst(getOrSetMethodName, 3);
        }
        return null;
    }

    public static String genSetter(String fieldName) {
        return upperFirstAndAddPre(fieldName, "set");
    }

    public static String genGetter(String fieldName) {
        return upperFirstAndAddPre(fieldName, "get");
    }

    public static String cutPreAndLowerFirst(String str, int preLength) {
        if (str == null) {
            return null;
        }
        if (str.length() > preLength) {
            char first = Character.toLowerCase(str.charAt(preLength));
            if (str.length() > preLength + 1) {
                return first + str.substring(preLength + 1);
            }
            return String.valueOf(first);
        }
        return null;
    }

    public static String upperFirstAndAddPre(String str, String preString) {
        if (str == null || preString == null) {
            return null;
        }
        return preString + upperFirst(str);
    }

    public static String upperFirst(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static String lowerFirst(String str) {
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    public static String removePrefix(String str, String prefix) {
        if (str != null && str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    public static String removePrefixIgnoreCase(String str, String prefix) {
        if (str != null && str.toLowerCase().startsWith(prefix.toLowerCase())) {
            return str.substring(prefix.length());
        }
        return str;
    }

    public static String removeSuffix(String str, String suffix) {
        if (str != null && str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    public static String removeSuffixIgnoreCase(String str, String suffix) {
        if (str != null && str.toLowerCase().endsWith(suffix.toLowerCase())) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    public static List<String> split(String str, char separator) {
        return split(str, separator, 0);
    }

    public static List<String> split(String str, char separator, int limit) {
        if (str == null) {
            return null;
        }
        List<String> list = new ArrayList<String>(limit == 0 ? 16 : limit);
        if (limit == 1) {
            list.add(str);
            return list;
        }

        boolean isNotEnd = true;
        int strLen = str.length();
        StringBuilder sb = new StringBuilder(strLen);
        for (int i = 0; i < strLen; i++) {
            char c = str.charAt(i);
            if (isNotEnd && c == separator) {
                list.add(sb.toString());
                sb.delete(0, sb.length());

                if (limit != 0 && list.size() == limit - 1) {
                    isNotEnd = false;
                }
            } else {
                sb.append(c);
            }
        }
        list.add(sb.toString());
        return list;
    }

    public static String[] split(String str, String delimiter) {
        if (str == null) {
            return null;
        }
        if (str.trim().length() == 0) {
            return new String[]{str};
        }

        int dellen = delimiter.length();    //del length
        int maxparts = (str.length() / dellen) + 2;        // one more for the last
        int[] positions = new int[maxparts];

        int i, j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = str.indexOf(delimiter, j)) != -1) {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = str.length();

        String[] result = new String[count];

        for (i = 0; i < count; i++) {
            result[i] = str.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }

    public static String repeat(char c, int count) {
        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }

    public static String convertCharset(String str, String sourceCharset, String destCharset) {
        if (isBlank(str) || isBlank(sourceCharset) || isBlank(destCharset)) {
            return str;
        }
        try {
            return new String(str.getBytes(sourceCharset), destCharset);
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    public static boolean equalsNotEmpty(String str1, String str2) {
        if (isEmpty(str1)) {
            return false;
        }
        return str1.equals(str2);
    }

    public static String format(String template, Object... values) {
        return String.format(template.replace("{}", "%s"), values);
    }

    public static String getDMY(String date) {
        Pattern pattern1 = Pattern.compile("^\\d{4}[/|-]+\\d{2}[/|-]+\\d{2}");
        Pattern pattern2 = Pattern.compile("^\\d{1,2}[/|-]+\\d{1,2}[/|-]+\\d{2,4}");
        Matcher matcher2 = pattern2.matcher(date);
        Matcher matcher1 = pattern1.matcher(date);
        String format;
        if (matcher2.find()) {
            format = "dmy";
        } else if (matcher1.find()) {
            format = "ymd";
        } else {
            return null;
        }

        String[] result = date.split("[^\\d]");
        if (result.length < 3) {
            return null;
        }
        if (format.toLowerCase().equals("dmy")) {

            if (result[0].length() < 2) {
                result[0] = "0".concat(result[0]);
            }
            if (result[1].length() < 2) {
                result[1] = "0".concat(result[1]);
            }
            if (result[2].length() == 2) {
                result[2] = "20".concat(result[2]);
            }
            date = result[0] + result[1] + result[2];
        } else if (format.toLowerCase().equals("ymd")) {
            if (result[0].length() == 2) {
                result[0] = "20".concat(result[0]);
            }
            if (result[1].length() < 2) {
                result[1] = "0".concat(result[1]);
            }
            if (result[2].length() < 2) {
                result[2] = "0".concat(result[2]);
            }
            date = result[2] + result[1] + result[0];
        } else {
        }
        return date;
    }

    public static boolean compareText(String key, String line, double scoreRate) {
        key = key.trim().toLowerCase();
        line = line.trim().toLowerCase();

        int levenshteinDistance = levenshteinDistance(key, line);
        int length = Math.max(key.length(), line.length());
        double score = 1.0 - (double) levenshteinDistance / length;
        return score > scoreRate;
    }

    public static int levenshteinDistance(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); ++i) {
            for (int j = 0; j <= y.length(); ++j) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    public static int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(2147483647);
    }

    public static String getCollectionTypeCode(String refNo) {
        //ma refNo 014CLLA1831200061 -> cat CLLA
        if (isBlank(refNo)) {
            return null;
        }
        if (refNo.length() < 7) {
            return null;
        }
        return refNo.substring(3, 7);
    }

    public static boolean containText(String left, String right) {
        if (left == null || right == null) {
            return true;
        }
        left = left.toLowerCase();
        right = right.toLowerCase();
        return left.contains(right) || right.contains(left);
    }

    public static String foldingAscii(String value) {
        if (isBlank(value)) {
            return value;
        }
        String nfdNormalizedString = Normalizer.normalize(value.toLowerCase(), Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").replace('đ', 'd').replaceAll(" +", " ");
    }

    public static String getCategoryAlias(String path) {
        if (isBlank(path)) {
            return null;
        } else {
            if (path.contains(AREA_SPLIT)) {
                return path.split(AREA_SPLIT)[0];
            } else {
                return path;
            }
        }
    }

    public static String getAreaAlias(String path) {
        if (isBlank(path)) {
            return null;
        } else {
            if (path.contains(AREA_SPLIT)) {
                return path.split(AREA_SPLIT)[1];
            } else {
                return null;
            }
        }
    }

    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static String html2text(String html) {
        if (html == null) {
            return null;
        }
        return Jsoup.parse(html).text();
    }

    public static String htmlDescription(String html) {
        String decs = html2text(html);
        if (decs != null) {
            if (decs.length() < 400) {
                return decs;
            } else {
                return decs.substring(0, 400);
            }
        }
        return "";
    }

    public static String emailFormat(String email) {
        String emailFormat = "";
        if (!StringUtils.isEmpty(email)) {
            for (int i = 0; i < email.length(); i++) {
                if (i >= CHAR_REPLACE_START && email.charAt(i) != CHAR_SHACKLE) {
                    emailFormat += STAR;
                    continue;
                } else if (email.charAt(i) == CHAR_SHACKLE) {
                    emailFormat += email.substring(i);
                    break;
                } else {
                    emailFormat += email.charAt(i);
                }
                ;
            }
        }
        return emailFormat;
    }

    public static String phoneNumberFormat(String phoneNumber) {
        String phoneFormat = "";
        if (!StringUtils.isEmpty(phoneNumber)) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (i >= CHAR_REPLACE_START) {
                    phoneFormat += STAR;
                } else {
                    phoneFormat += phoneNumber.charAt(i);
                }
            }
        }
        return phoneFormat;
    }

    public static String maskUserName(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }

        int length = name.length();
        if (length <= 5) {
            return maskString(name, STAR, 1, length);
        }
        if (length < 10) {
            return maskString(name, STAR, CHAR_REPLACE_START, length);
        }
        return maskString(name, STAR, 2, length - 2);

    }

    private static String maskString(String strText, String maskChar, int start, int end) {

        if (StringUtils.isEmpty(strText))
            return "";

        if (start < 0)
            start = 0;

        if (end > strText.length())
            end = strText.length();

        int maskLength = end - start;

        if (maskLength == 0)
            return maskChar;

        StringBuilder sbMaskString = new StringBuilder(maskLength);

        for (int i = 0; i < maskLength; i++) {
            sbMaskString.append(maskChar);
        }

        return strText.substring(0, start)
                + sbMaskString.toString()
                + strText.substring(start + maskLength);
    }

    public static String addressFormat(String address) {
        if (!StringUtils.isEmpty(address)) {
            for (char c : address.toCharArray()) {
                if (c == COMMA) {
                    String[] strings = address.split(String.valueOf(COMMA));
                    if (strings.length >= 2) {
                        return strings[strings.length - 2] + COMMA + strings[strings.length - 1];
                    }
                }
            }
        }
        return address;
    }

    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static String escapeLikeParam(String param) {
        return MessageFormat.format("%{0}%",
                RegExUtils.replaceAll(param, LIKE_ESCAPE.getFirst(),
                        LIKE_ESCAPE.getSecond()));
    }

    public static String getOrderByClause(List<Sort.Order> orders) {

        StringBuilder sb = new StringBuilder();
        Iterator<Sort.Order> it = orders.iterator();
        while (it.hasNext()) {
            Sort.Order order = it.next();
            sb.append(SPACE)
                    .append(order.getProperty())
                    .append(SPACE)
                    .append(order.getDirection())
                    .append(SPACE);
            if (it.hasNext()) {
                sb.append(COMMA);
            }
        }
        return sb.toString();
    }

    public static String hideNumberApartment(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append(input);
        String s = input.toLowerCase();
        int stressIndex = s.indexOf("đường");
        if (stressIndex != -1) return sb.substring(stressIndex);
        int townIndex = s.indexOf("phường");
        if (townIndex != -1) return sb.substring(townIndex);
        int communeIndex = s.indexOf("xã");
        if (communeIndex != -1) return sb.substring(communeIndex);
        return input;
    }

    public static MultiValueMap<String, String> separateStringAndNumber(String input, MultiValueMap<String, String> queryParams) {
        String st1 = input.replaceAll("[^A-Za-z]", "");
        String st2 = input.replaceAll("[^0-9]", "");
        queryParams.add("keyword", st1);
        queryParams.add("phoneNumber", st2);
        return queryParams;
    }

    public static String getStringFromBundle(String key, String lang, List<String> params) {
        Locale locale = new Locale(lang);
        ResourceBundle messages = ResourceBundle.getBundle("i18n/messages", locale);
        String value = messages.getString(key);
        if (params != null && !params.isEmpty()) {
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(locale);
            formatter.applyPattern(value);
            return formatter.format(params.toArray());
        } else {
            return value;
        }
    }
    public static List<Long> convertList(String string){
        String[] array =  string.split(",");
        List<Long> result = new ArrayList<>();
        for (String s : array) {
            result.add(Long.valueOf(s));
        }
        return result;
    }
}
