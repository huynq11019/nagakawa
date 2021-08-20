/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nagakawa.guarantee.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author linhlh2
 */
public class GetterUtil {

	public static String[] BOOLEANS = { "true", "t", "y", "on", "1" };
	public static final boolean DEFAULT_BOOLEAN = false;
	public static final boolean[] DEFAULT_BOOLEAN_VALUES = new boolean[0];
	public static final Boolean[] DEFAULT_BOOLEANS = new Boolean[0];
	public static final double DEFAULT_DOUBLE = 0.0;
	public static final double[] DEFAULT_DOUBLE_VALUES = new double[0];
	public static final Double[] DEFAULT_DOUBLES = new Double[0];
	public static final float DEFAULT_FLOAT = 0;
	public static final float[] DEFAULT_FLOAT_VALUES = new float[0];
	public static final Float[] DEFAULT_FLOATS = new Float[0];
	public static final int DEFAULT_INTEGER = 0;
	public static final int[] DEFAULT_INTEGER_VALUES = new int[0];
	public static final Integer[] DEFAULT_INTEGERS = new Integer[0];
	public static final long DEFAULT_LONG = 0;
	public static final long[] DEFAULT_LONG_VALUES = new long[0];
	public static final Long[] DEFAULT_LONGS = new Long[0];
	public static final String DEFAULT_PASSWORD = "123456a@";
	public static final short DEFAULT_SHORT = 0;
	public static final short[] DEFAULT_SHORT_VALUES = new short[0];
	public static final Short[] DEFAULT_SHORTS = new Short[0];
	public static final String DEFAULT_STRING = StringPool.BLANK;

	private static String _trim(String value) {
		if (value != null) {
			value = value.trim();

			StringBuilder sb = new StringBuilder();

			char[] charArray = value.toCharArray();

			for (int i = 0; i < charArray.length; i++) {
				if ((Character.isDigit(charArray[i])) || ((charArray[i] == CharPool.DASH) && (i == 0))
						|| (charArray[i] == CharPool.PERIOD) || (charArray[i] == CharPool.UPPER_CASE_E)
						|| (charArray[i] == CharPool.LOWER_CASE_E)) {

					sb.append(charArray[i]);
				}
			}

			value = sb.toString();
		}

		return value;
	}

	public static boolean get(String value, boolean defaultValue) {
		if (value != null) {
			try {
				value = value.trim();

				if (value.equalsIgnoreCase(BOOLEANS[0]) || value.equalsIgnoreCase(BOOLEANS[1])
						|| value.equalsIgnoreCase(BOOLEANS[2]) || value.equalsIgnoreCase(BOOLEANS[3])
						|| value.equalsIgnoreCase(BOOLEANS[4])) {

					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
			}
		}

		return defaultValue;
	}

	public static Boolean get(String value, Boolean defaultValue) {
		try {
			return Boolean.valueOf(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static Date get(String value, DateFormat df, Date defaultValue) {
		try {
			Date date = df.parse(value.trim());

			if (date != null) {
				return date;
			}
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static double get(String value, double defaultValue) {
		try {
			return Double.parseDouble(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static Double get(String value, Double defaultValue) {
		try {
			return Double.valueOf(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static float get(String value, float defaultValue) {
		try {
			return Float.parseFloat(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static Float get(String value, Float defaultValue) {
		try {
			return Float.valueOf(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static int get(String value, int defaultValue) {
		try {
			return Integer.parseInt(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static Integer get(String value, Integer defaultValue) {
		try {
			return Integer.valueOf(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static long get(String value, long defaultValue) {
		try {
			return Long.parseLong(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static Long get(String value, Long defaultValue) {
		try {
			return Long.valueOf(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static short get(String value, short defaultValue) {
		try {
			return Short.parseShort(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static Short get(String value, Short defaultValue) {
		try {
			return Short.valueOf(_trim(value));
		} catch (Exception e) {
		}

		return defaultValue;
	}

	public static String get(String value, String defaultValue) {
		if (value != null) {
			value = value.trim();
			value = StringUtil.replace(value, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);

			return value;
		}

		return defaultValue;
	}

	public static String[] getArray(String str, String regex) {
		if (str == null) {
			return new String[0];
		}

		return str.split(regex);
	}

	public static Boolean getBoolean(Object value) {
		if (value == null) {
			return null;
		}

		return getBoolean(value.toString());

	}

	public static Boolean getBoolean(String value) {
		return getBoolean(value, null);
	}

	public static Boolean getBoolean(String value, Boolean defaultValue) {
		return get(value, defaultValue);
	}

	public static Boolean[] getBooleans(String[] values) {
		return getBooleans(values, DEFAULT_BOOLEANS);
	}

	public static Boolean[] getBooleans(String[] values, Boolean[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Boolean[] booleanValues = new Boolean[values.length];

		for (int i = 0; i < values.length; i++) {
			booleanValues[i] = getBoolean(values[i]);
		}

		return booleanValues;
	}

	public static boolean getBooleanValue(Object value, boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return get(value.toString(), defaultValue);
	}

	public static boolean getBooleanValue(String value) {
		return getBooleanValue(value, DEFAULT_BOOLEAN);
	}

	public static boolean getBooleanValue(String value, boolean defaultValue) {
		return get(value, defaultValue);
	}

	public static boolean[] getBooleanValues(String[] values) {
		return getBooleanValues(values, DEFAULT_BOOLEAN_VALUES);
	}

	public static boolean[] getBooleanValues(String[] values, boolean[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		boolean[] booleanValues = new boolean[values.length];

		for (int i = 0; i < values.length; i++) {
			booleanValues[i] = getBoolean(values[i]);
		}

		return booleanValues;
	}

	public static String getDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		return DateUtils.getDate(date, pattern);
	}

	public static Date getDate(String value, DateFormat df) {
		return getDate(value, df, new Date());
	}

	public static Date getDate(String value, DateFormat df, Date defaultValue) {
		return get(value, df, defaultValue);
	}

	public static Date getDate(String value, String pattern) {
		DateFormat df = DateFormatFactoryUtil.getSimpleDateFormat(pattern);

		return getDate(value, df);
	}

	public static Double getDouble(Object value) {
		if (value == null) {
			return null;
		}

		return getDouble(value.toString());

	}

	public static Double getDouble(String value) {
		return getDouble(value, null);
	}

	public static Double getDouble(String value, Double defaultValue) {
		return get(value, defaultValue);
	}

	public static Double[] getDoubles(String[] values) {
		return getDoubles(values, DEFAULT_DOUBLES);
	}

	public static Double[] getDoubles(String[] values, Double[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Double[] doubleValues = new Double[values.length];

		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = getDouble(values[i]);
		}

		return doubleValues;
	}

	public static double getDoubleValue(String value) {
		return getDoubleValue(value, DEFAULT_DOUBLE);
	}

	public static double getDoubleValue(String value, double defaultValue) {
		return get(value, defaultValue);
	}

	public static double[] getDoubleValues(String[] values) {
		return getDoubleValues(values, DEFAULT_DOUBLE_VALUES);
	}

	public static double[] getDoubleValues(String[] values, double[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		double[] doubleValues = new double[values.length];

		for (int i = 0; i < values.length; i++) {
			doubleValues[i] = getDouble(values[i]);
		}

		return doubleValues;
	}

	public static Float getFloat(Object value) {
		if (value == null) {
			return null;
		}

		return getFloat(value.toString());

	}

	public static Float getFloat(String value) {
		return getFloat(value, null);
	}

	public static Float getFloat(String value, Float defaultValue) {
		return get(value, defaultValue);
	}

	public static Float[] getFloats(String[] values) {
		return getFloats(values, DEFAULT_FLOATS);
	}

	public static Float[] getFloats(String[] values, Float[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Float[] floatValues = new Float[values.length];

		for (int i = 0; i < values.length; i++) {
			floatValues[i] = getFloat(values[i]);
		}

		return floatValues;
	}

	public static float getFloatValue(String value) {
		return getFloatValue(value, DEFAULT_FLOAT);
	}

	public static float getFloatValue(String value, float defaultValue) {
		return get(value, defaultValue);
	}

	public static float[] getFloatValues(String[] values) {
		return getFloatValues(values, DEFAULT_FLOAT_VALUES);
	}

	public static float[] getFloatValues(String[] values, float[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		float[] floatValues = new float[values.length];

		for (int i = 0; i < values.length; i++) {
			floatValues[i] = getFloat(values[i]);
		}

		return floatValues;
	}

	public static String getFormat(Long value) {
		if (value == null) {
			return StringPool.BLANK;
		}

		return NumberFormat.getInstance().format(value);
	}

	public static Integer getInteger(Object value) {
		if (value == null) {
			return null;
		}

		return getInteger(value.toString());

	}

	public static Integer getInteger(String value) {
		return getInteger(value, null);
	}

	public static Integer getInteger(String value, Integer defaultValue) {
		return get(value, defaultValue);
	}

	public static Integer[] getIntegers(String[] values) {
		return getIntegers(values, DEFAULT_INTEGERS);
	}

	public static Integer[] getIntegers(String[] values, Integer[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		Integer[] intValues = new Integer[values.length];

		for (int i = 0; i < values.length; i++) {
			intValues[i] = getInteger(values[i]);
		}

		return intValues;
	}

	public static int getIntegerValue(Object value) {
		return getIntegerValue(value, DEFAULT_INTEGER);
	}

	public static int getIntegerValue(Object value, int defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return getIntegerValue(getString(value), defaultValue);
	}

	public static int getIntegerValue(String value) {
		return getIntegerValue(value, DEFAULT_INTEGER);
	}

	public static int getIntegerValue(String value, int defaultValue) {
		return get(value, defaultValue);
	}

	public static int[] getIntegerValues(String[] values) {
		return getIntegerValues(values, DEFAULT_INTEGER_VALUES);
	}

	public static int[] getIntegerValues(String[] values, int[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		int[] intValues = new int[values.length];

		for (int i = 0; i < values.length; i++) {
			intValues[i] = getInteger(values[i]);
		}

		return intValues;
	}

	public static long getLong(Object value, long defaultValue) {
		if (value == null) {
			return defaultValue;
		}

		return getLong(value.toString());
	}

	public static Long getLong(Object value) {
		if (value == null) {
			return null;
		}

		return getLong(value.toString());
	}

	public static Long getLong(String value) {
		return getLong(value, null);
	}

	public static Long getLong(String value, Long defaultValue) {
		return get(value, defaultValue);
	}

	public static Long[] getLongs(String[] values) {
		return getLongs(values, DEFAULT_LONGS);
	}

	public static Long[] getLongs(String[] values, Long[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		Long[] longValues = new Long[values.length];

		for (int i = 0; i < values.length; i++) {
			longValues[i] = getLong(values[i]);
		}

		return longValues;
	}

	public static long getLongValue(String value) {
		return getLongValue(value, DEFAULT_LONG);
	}

	public static long getLongValue(String value, long defaultValue) {
		return get(value, defaultValue);
	}

	public static long[] getLongValues(String[] values) {
		return getLongValues(values, DEFAULT_LONG_VALUES);
	}
	
	public static List<Long> getLongValues(List<String> values) {
		if (values == null) {
			return new ArrayList<>();
		}

		List<Long> longValues = new ArrayList<>();

		for (String value: values) {
			longValues.add(getLong(value));
		}

		return longValues;
	}
	
	public static long[] getLongValues(String[] values, long[] defaultValue) {
		if (values == null) {
			return defaultValue;
		}

		long[] longValues = new long[values.length];

		for (int i = 0; i < values.length; i++) {
			longValues[i] = getLong(values[i]);
		}

		return longValues;
	}

	public static String getServerUrl(HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();

		sb.append(req.getScheme());
		sb.append(StringPool.COLON);
		sb.append(StringPool.DOUBLE_SLASH);

		String serverName = req.getServerName();

		if (serverName.equalsIgnoreCase("localhost")) {
			try {
				sb.append(InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException ex) {
				sb.append(serverName);
			}
		} else {
			sb.append(serverName);
		}

		if (req.getServerPort() != 80) {
			sb.append(StringPool.COLON);
			sb.append(req.getServerPort());
		}

//        sb.append(StringPool.SLASH);
		sb.append(req.getContextPath());

		return sb.toString();
	}

	public static Short getShort(Object value) {
		if (value == null) {
			return null;
		}

		return getShort(value.toString());

	}

	public static Short getShort(String value) {
		return getShort(value, DEFAULT_SHORT);
	}

	public static Short getShort(String value, Short defaultValue) {
		return get(value, defaultValue);
	}

	public static Short[] getShorts(String[] values) {
		return getShorts(values, DEFAULT_SHORTS);
	}

	public static Short[] getShorts(String[] values, Short[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		Short[] shortValues = new Short[values.length];

		for (int i = 0; i < values.length; i++) {
			shortValues[i] = getShort(values[i]);
		}

		return shortValues;
	}

	public static short getShortValue(String value) {
		return getShortValue(value, DEFAULT_SHORT);
	}

	public static short getShortValue(String value, short defaultValue) {
		return get(value, defaultValue);
	}

	public static short[] getShortValues(String[] values) {
		return getShortValues(values, DEFAULT_SHORT_VALUES);
	}

	public static short[] getShortValues(String[] values, short[] defaultValue) {

		if (values == null) {
			return defaultValue;
		}

		short[] shortValues = new short[values.length];

		for (int i = 0; i < values.length; i++) {
			shortValues[i] = getShort(values[i]);
		}

		return shortValues;
	}

	public static String getStatusColorName(Long status, String[] statusColorArray) {
		String statusName = StringPool.BLANK;

		if (status != null && status > 0 && status < statusColorArray.length) {
			statusName = "color:#" + statusColorArray[status.intValue()];
		}

		return statusName;
	}

	public static String getStatusName(Long status, String[] statusArray) {
		String statusName = StringPool.BLANK;

		if (status != null && status < statusArray.length) {
			statusName = statusArray[status.intValue()];
		}

		return statusName;
	}

	public static String getString(Object value) {
		if (value == null) {
			return null;
		}

		return getString(value.toString());

	}

	public static String getString(Object value, String defaultValue) {
		if (value == null) {
			return StringPool.BLANK;
		}

		return value.toString();

	}

	public static String getString(String value) {
		return getString(value, DEFAULT_STRING);
	}

	public static String getString(String value, String defaultValue) {
		return get(value, defaultValue);
	}

	public static String getStringLower(String value) {
		if (value == null) {
			return null;
		}

		return getString(value).toLowerCase();
	}

	public static String getStringUpper(String value) {
		if (value == null) {
			return null;
		}

		return getString(value).toUpperCase();
	}
}
