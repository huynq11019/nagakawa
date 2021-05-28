/**
 * Copyright (C) 2012 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nagakawa.guarantee.util;

import java.text.Format;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author os_linhlh2
 */
public class FastDateFormatFactoryUtil {

	private static Map<String, Format> _dateFormats = new ConcurrentHashMap<String, Format>();
	private static Map<String, Format> _dateTimeFormats = new ConcurrentHashMap<String, Format>();
	private static Locale _locale = new Locale("vi", "VN");
	private static Map<String, Format> _simpleDateFormats = new ConcurrentHashMap<String, Format>();

	private static Map<String, Format> _timeFormats = new ConcurrentHashMap<String, Format>();

	public static final int FULL = 0;

	public static final int LONG = 1;

	public static final int MEDIUM = 2;

	public static final int SHORT = 3;

	public static Format getDate(int style, Locale locale, TimeZone timeZone) {
		String key = getKey(style, locale, timeZone);

		Format format = _dateFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getDateInstance(style, timeZone, locale);

			_dateFormats.put(key, format);
		}

		return format;
	}

	public static Format getDate(Locale locale) {
		return getDate(locale, null);
	}

	public static Format getDate(Locale locale, TimeZone timeZone) {
		return getDate(SHORT, locale, timeZone);
	}

	public static Format getDate(TimeZone timeZone) {
		return getDate(_locale, timeZone);
	}

	public static Format getDateTime(int dateStyle, int timeStyle, Locale locale, TimeZone timeZone) {

		String key = getKey(dateStyle, timeStyle, locale, timeZone);

		Format format = _dateTimeFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getDateTimeInstance(dateStyle, timeStyle, timeZone, locale);

			_dateTimeFormats.put(key, format);
		}

		return format;
	}

	public static Format getDateTime(Locale locale) {
		return getDateTime(locale, null);
	}

	public static Format getDateTime(Locale locale, TimeZone timeZone) {
		return getDateTime(SHORT, SHORT, locale, timeZone);
	}

	public static Format getDateTime(TimeZone timeZone) {
		return getDateTime(_locale, timeZone);
	}

	protected static String getKey(Object... arguments) {
		StringBuilder sb = new StringBuilder(arguments.length * 2 - 1);

		for (int i = 0; i < arguments.length; i++) {
			sb.append(arguments[i]);

			if ((i + 1) < arguments.length) {
				sb.append(StringPool.UNDERLINE);
			}
		}

		return sb.toString();
	}

	public static Format getSimpleDateFormat(String pattern) {
		return getSimpleDateFormat(pattern, _locale, null);
	}

	public static Format getSimpleDateFormat(String pattern, Locale locale) {
		return getSimpleDateFormat(pattern, locale, null);
	}

	public static Format getSimpleDateFormat(String pattern, Locale locale, TimeZone timeZone) {

		String key = getKey(pattern, locale, timeZone);

		Format format = _simpleDateFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getInstance(pattern, timeZone, locale);

			_simpleDateFormats.put(key, format);
		}

		return format;
	}

	public static Format getSimpleDateFormat(String pattern, TimeZone timeZone) {
		return getSimpleDateFormat(pattern, _locale, timeZone);
	}

	public static Format getTime(int style, Locale locale, TimeZone timeZone) {
		String key = getKey(style, locale, timeZone);

		Format format = _timeFormats.get(key);

		if (format == null) {
			format = FastDateFormat.getTimeInstance(style, timeZone, locale);

			_timeFormats.put(key, format);
		}

		return format;
	}

	public static Format getTime(Locale locale) {
		return getTime(locale, null);
	}

	public static Format getTime(Locale locale, TimeZone timeZone) {
		return getTime(SHORT, locale, timeZone);
	}

	public static Format getTime(TimeZone timeZone) {
		return getTime(_locale, timeZone);
	}
}
