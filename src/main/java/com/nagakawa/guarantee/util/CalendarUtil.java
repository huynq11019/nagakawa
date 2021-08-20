/**
 * Copyright (C) 2012 Evotek Telecom. All rights reserved.
 * Evotek PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.nagakawa.guarantee.util;

import java.sql.Timestamp;
import java.text.Format;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author os_linhlh2
 */
public class CalendarUtil {

	/** The month ids. */
	public static int[] MONTH_IDS = new int[] { Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH, Calendar.APRIL,
			Calendar.MAY, Calendar.JUNE, Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER, Calendar.OCTOBER,
			Calendar.NOVEMBER, Calendar.DECEMBER };

	/** The calendar pool. */
	private static Map<String, String[]> _calendarPool = new ConcurrentHashMap<>();

	/**
	 * After by day.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if successful
	 */
	public static boolean afterByDay(Date date1, Date date2) {
		long millis1 = _getTimeInMillis(date1);
		long millis2 = _getTimeInMillis(date2);

		if (millis1 > millis2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Before by day.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if successful
	 */
	public static boolean beforeByDay(Date date1, Date date2) {
		long millis1 = _getTimeInMillis(date1);
		long millis2 = _getTimeInMillis(date2);

		if (millis1 < millis2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Equals by day.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return true, if successful
	 */
	public static boolean equalsByDay(Date date1, Date date2) {
		long millis1 = _getTimeInMillis(date1);
		long millis2 = _getTimeInMillis(date2);

		if (millis1 == millis2) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the age.
	 *
	 * @param date  the date
	 * @param today the today
	 * @return the age
	 */
	public static int getAge(Date date, Calendar today) {
		Calendar birthday = Calendar.getInstance();

		birthday.setTime(date);

		int yearDiff = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);

		if (today.get(Calendar.MONTH) < birthday.get(Calendar.MONTH)) {
			yearDiff--;
		} else if (today.get(Calendar.MONTH) == birthday.get(Calendar.MONTH)
				&& today.get(Calendar.DATE) < birthday.get(Calendar.DATE)) {

			yearDiff--;
		}

		return yearDiff;
	}

	/**
	 * Gets the age.
	 *
	 * @param date the date
	 * @param tz   the tz
	 * @return the age
	 */
	public static int getAge(Date date, TimeZone tz) {
		return getAge(date, Calendar.getInstance(tz));
	}

	/**
	 * Gets the calendar.
	 *
	 * @param date the date
	 * @return the calendar
	 */
	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		return cal;
	}

	/**
	 * Gets the date.
	 *
	 * @param year      the year
	 * @param month     the month
	 * @param date      the date
	 * @param hourOfDay the hour of day
	 * @param minute    the minute
	 * @param second    the second
	 * @return the date
	 */
	public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();

		cal.set(year, month, date, hourOfDay, minute, second);

		return cal.getTime();
	}

	/**
	 * Gets the date from hour.
	 *
	 * @param hourOfDay the hour of day
	 * @param minute    the minute
	 * @param second    the second
	 * @return the date from hour
	 */
	public static Date getDateFromHour(int hourOfDay, int minute, int second) {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);

		return cal.getTime();
	}

	/**
	 * Gets the days.
	 *
	 * @param locale the locale
	 * @return the days
	 */
	public static String[] getDays(Locale locale) {
		return getDays(locale, null);
	}

	/**
	 * Gets the days.
	 *
	 * @param locale  the locale
	 * @param pattern the pattern
	 * @return the days
	 */
	public static String[] getDays(Locale locale, String pattern) {
		if (Validator.isNull(pattern)) {
			pattern = "EEEE";
		}

		StringBuilder sb = new StringBuilder(6);

		sb.append("days_");
		sb.append(pattern);
		sb.append("_");
		sb.append(locale.getLanguage());
		sb.append("_");
		sb.append(locale.getCountry());

		String key = sb.toString();

		String[] days = _calendarPool.get(key);

		if (days == null) {
			days = new String[7];

			Format dayFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern, locale);

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.DATE, 1);

			for (int i = 0; i < 7; i++) {
				cal.set(Calendar.DAY_OF_WEEK, i + 1);

				days[i] = dayFormat.format(cal.getTime());
			}

			_calendarPool.put(key, days);
		}

		return days;
	}

	/**
	 * Gets the days in month.
	 *
	 * @param cal the cal
	 * @return the days in month
	 */
	public static int getDaysInMonth(Calendar cal) {
		return getDaysInMonth(cal.get(Calendar.MONTH), cal.get(Calendar.YEAR));
	}

	/**
	 * Gets the days in month.
	 *
	 * @param month the month
	 * @param year  the year
	 * @return the days in month
	 */
	public static int getDaysInMonth(int month, int year) {
		month++;

		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {

			return 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {

			return 30;
		} else {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {

				return 29;
			} else {
				return 28;
			}
		}
	}

	/**
	 * Gets the gregorian day.
	 *
	 * @param cal the cal
	 * @return the gregorian day
	 */
	public static int getGregorianDay(Calendar cal) {
		int year = cal.get(Calendar.YEAR) - 1600;

		int month = cal.get(Calendar.MONTH) + 1;

		if (month < 3) {
			month += 12;
		}

		int day = cal.get(Calendar.DATE);

		int gregorianDay = (int) (6286 + year * 365.25 - year / 100 + year / 400 + 30.6 * month + 0.2 + day);

		return gregorianDay;
	}

	/**
	 * Gets the GT date.
	 *
	 * @param cal the cal
	 * @return the GT date
	 */
	public static Date getGTDate(Calendar cal) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.HOUR_OF_DAY, 0);
		gtCal.set(Calendar.MINUTE, 0);
		gtCal.set(Calendar.SECOND, 0);
		gtCal.set(Calendar.MILLISECOND, 0);

		return gtCal.getTime();
	}

	/**
	 * Gets the GT date.
	 *
	 * @param cal       the cal
	 * @param hourOfDay the hour of day
	 * @return the GT date
	 */
	public static Date getGTDate(Calendar cal, int hourOfDay) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		gtCal.set(Calendar.MINUTE, 0);
		gtCal.set(Calendar.SECOND, 0);
		gtCal.set(Calendar.MILLISECOND, 0);

		return gtCal.getTime();
	}

	/**
	 * Gets the GT date another day.
	 *
	 * @param cal       the cal
	 * @param changeDay the change day
	 * @return the GT date another day
	 */
	public static Date getGTDateAnotherDay(Calendar cal, int changeDay) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.add(Calendar.DATE, changeDay);

		return getGTDate(gtCal);
	}

	/**
	 * Gets the GT date another day.
	 *
	 * @param cal       the cal
	 * @param hourOfDay the hour of day
	 * @param changeDay the change day
	 * @return the GT date another day
	 */
	public static Date getGTDateAnotherDay(Calendar cal, int hourOfDay, int changeDay) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.add(Calendar.DATE, changeDay);

		return getGTDate(gtCal, hourOfDay);
	}

	/**
	 * Gets the GT date of month.
	 *
	 * @param cal the cal
	 * @return the GT date of month
	 */
	public static Date getGTDateOfMonth(Calendar cal) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.DAY_OF_MONTH, gtCal.getActualMinimum(Calendar.DAY_OF_MONTH));

		return getGTDate(gtCal);
	}

	/**
	 * Gets the GT date of month.
	 *
	 * @param cal        the cal
	 * @param dayOfMonth the day of month
	 * @return the GT date of month
	 */
	public static Date getGTDateOfMonth(Calendar cal, int dayOfMonth) {
		Calendar gtCal = (Calendar) cal.clone();

		if (dayOfMonth >= gtCal.getActualMinimum(Calendar.DAY_OF_MONTH)
				&& dayOfMonth <= gtCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			gtCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		} else {
			gtCal.set(Calendar.DAY_OF_MONTH, gtCal.getActualMinimum(Calendar.DAY_OF_MONTH));
		}

		return getGTDate(gtCal);
	}

	/**
	 * Gets the GT date of week.
	 *
	 * @param cal the cal
	 * @return the GT date of week
	 */
	public static Date getGTDateOfWeek(Calendar cal) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.DAY_OF_WEEK, gtCal.getFirstDayOfWeek());

		return getGTDate(gtCal);
	}

	/**
	 * Gets the GT date of week.
	 *
	 * @param cal       the cal
	 * @param dayOfWeek the day of week
	 * @return the GT date of week
	 */
	public static Date getGTDateOfWeek(Calendar cal, int dayOfWeek) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

		return getGTDate(gtCal);
	}

	/**
	 * Gets the GT date of week.
	 *
	 * @param cal       the cal
	 * @param dayOfWeek the day of week
	 * @param hour      the hour
	 * @return the GT date of week
	 */
	public static Date getGTDateOfWeek(Calendar cal, int dayOfWeek, int hour) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

		return getGTDate(gtCal, hour);
	}

	public static Instant getGTInstant(Calendar cal) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.set(Calendar.HOUR_OF_DAY, 0);
		gtCal.set(Calendar.MINUTE, 0);
		gtCal.set(Calendar.SECOND, 0);
		gtCal.set(Calendar.MILLISECOND, 0);

		return gtCal.toInstant();
	}

	public static Instant getGTInstantAnotherDay(Calendar cal, int changeDay) {
		Calendar gtCal = (Calendar) cal.clone();

		gtCal.add(Calendar.DATE, changeDay);

		return getGTInstant(gtCal);
	}

	/**
	 * Gets the last day of week.
	 *
	 * @param cal the cal
	 * @return the last day of week
	 */
	public static int getLastDayOfWeek(Calendar cal) {
		int firstDayOfWeek = cal.getFirstDayOfWeek();

		if (firstDayOfWeek == Calendar.SUNDAY) {
			return Calendar.SATURDAY;
		} else if (firstDayOfWeek == Calendar.MONDAY) {
			return Calendar.SUNDAY;
		} else if (firstDayOfWeek == Calendar.TUESDAY) {
			return Calendar.MONDAY;
		} else if (firstDayOfWeek == Calendar.WEDNESDAY) {
			return Calendar.TUESDAY;
		} else if (firstDayOfWeek == Calendar.THURSDAY) {
			return Calendar.WEDNESDAY;
		} else if (firstDayOfWeek == Calendar.FRIDAY) {
			return Calendar.THURSDAY;
		}

		return Calendar.FRIDAY;
	}

	/**
	 * Gets the LT date.
	 *
	 * @param cal the cal
	 * @return the LT date
	 */
	public static Date getLTDate(Calendar cal) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.HOUR_OF_DAY, 23);
		ltCal.set(Calendar.MINUTE, 59);
		ltCal.set(Calendar.SECOND, 59);
		ltCal.set(Calendar.MILLISECOND, 990);

		return ltCal.getTime();
	}

	/**
	 * Gets the LT date.
	 *
	 * @param cal       the cal
	 * @param hourOfDay the hour of day
	 * @return the LT date
	 */
	public static Date getLTDate(Calendar cal, int hourOfDay) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		ltCal.set(Calendar.MINUTE, 59);
		ltCal.set(Calendar.SECOND, 59);
		ltCal.set(Calendar.MILLISECOND, 990);

		return ltCal.getTime();
	}

	/**
	 * Gets the LT date another day.
	 *
	 * @param cal       the cal
	 * @param changeDay the change day
	 * @return the LT date another day
	 */
	public static Date getLTDateAnotherDay(Calendar cal, int changeDay) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.add(Calendar.DATE, changeDay);

		return getLTDate(ltCal);
	}

	/**
	 * Gets the LT date another day.
	 *
	 * @param cal       the cal
	 * @param hourOfDay the hour of day
	 * @param changeDay the change day
	 * @return the LT date another day
	 */
	public static Date getLTDateAnotherDay(Calendar cal, int hourOfDay, int changeDay) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.add(Calendar.DATE, changeDay);

		return getLTDate(ltCal, hourOfDay);
	}

	/**
	 * Gets the LT date of month.
	 *
	 * @param cal the cal
	 * @return the LT date of month
	 */
	public static Date getLTDateOfMonth(Calendar cal) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.DAY_OF_MONTH, ltCal.getActualMaximum(Calendar.DAY_OF_MONTH));

		return getLTDate(ltCal);
	}

	/**
	 * Gets the LT date of month.
	 *
	 * @param cal        the cal
	 * @param dayOfMonth the day of month
	 * @return the LT date of month
	 */
	public static Date getLTDateOfMonth(Calendar cal, int dayOfMonth) {
		Calendar ltCal = (Calendar) cal.clone();

		if (dayOfMonth >= ltCal.getActualMinimum(Calendar.DAY_OF_MONTH)
				&& dayOfMonth <= ltCal.getActualMaximum(Calendar.DAY_OF_MONTH)) {
			ltCal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		} else {
			ltCal.set(Calendar.DAY_OF_MONTH, ltCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}

		return getLTDate(ltCal);
	}

	/**
	 * Gets the LT date of week.
	 *
	 * @param cal the cal
	 * @return the LT date of week
	 */
	public static Date getLTDateOfWeek(Calendar cal) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.DAY_OF_WEEK, getLastDayOfWeek(ltCal));

		return getLTDate(ltCal);
	}

	/**
	 * Gets the LT date of week.
	 *
	 * @param cal       the cal
	 * @param dayOfWeek the day of week
	 * @return the LT date of week
	 */
	public static Date getLTDateOfWeek(Calendar cal, int dayOfWeek) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

		return getLTDate(ltCal);
	}

	/**
	 * Gets the LT date of week.
	 *
	 * @param cal       the cal
	 * @param dayOfWeek the day of week
	 * @param hour      the hour
	 * @return the LT date of week
	 */
	public static Date getLTDateOfWeek(Calendar cal, int dayOfWeek, int hour) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

		return getLTDate(ltCal, hour);
	}

	public static Instant getLTInstant(Calendar cal) {
		Calendar ltCal = (Calendar) cal.clone();

		ltCal.set(Calendar.HOUR_OF_DAY, 23);
		ltCal.set(Calendar.MINUTE, 59);
		ltCal.set(Calendar.SECOND, 59);
		ltCal.set(Calendar.MILLISECOND, 990);

		return ltCal.toInstant();
	}

	/**
	 * Gets the month ids.
	 *
	 * @return the month ids
	 */
	public static int[] getMonthIds() {
		return MONTH_IDS;
	}

	/**
	 * Gets the months.
	 *
	 * @param locale the locale
	 * @return the months
	 */
	public static String[] getMonths(Locale locale) {
		return getMonths(locale, null);
	}

	/**
	 * Gets the months.
	 *
	 * @param locale  the locale
	 * @param pattern the pattern
	 * @return the months
	 */
	public static String[] getMonths(Locale locale, String pattern) {
		if (Validator.isNull(pattern)) {
			pattern = "MMMM";
		}

		StringBuilder sb = new StringBuilder(6);

		sb.append("months_");
		sb.append(pattern);
		sb.append("_");
		sb.append(locale.getLanguage());
		sb.append("_");
		sb.append(locale.getCountry());

		String key = sb.toString();

		String[] months = _calendarPool.get(key);

		if (months == null) {
			months = new String[12];

			Format monthFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern, locale);

			Calendar cal = Calendar.getInstance();

			cal.set(Calendar.DATE, 1);

			for (int i = 0; i < 12; i++) {
				cal.set(Calendar.MONTH, i);

				months[i] = monthFormat.format(cal.getTime());
			}

			_calendarPool.put(key, months);
		}

		return months;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @param date the date
	 * @return the timestamp
	 */
	public static Timestamp getTimestamp(Date date) {
		if (date == null) {
			return null;
		} else {
			return new Timestamp(date.getTime());
		}
	}

	/**
	 * Checks if is after.
	 *
	 * @param month1   the month 1
	 * @param day1     the day 1
	 * @param year1    the year 1
	 * @param hour1    the hour 1
	 * @param minute1  the minute 1
	 * @param amPm1    the am pm 1
	 * @param month2   the month 2
	 * @param day2     the day 2
	 * @param year2    the year 2
	 * @param hour2    the hour 2
	 * @param minute2  the minute 2
	 * @param amPm2    the am pm 2
	 * @param timeZone the time zone
	 * @param locale   the locale
	 * @return true, if is after
	 */
	public static boolean isAfter(int month1, int day1, int year1, int hour1, int minute1, int amPm1, int month2,
			int day2, int year2, int hour2, int minute2, int amPm2, TimeZone timeZone, Locale locale) {

		Calendar cal1 = Calendar.getInstance(timeZone, locale);

		cal1.set(Calendar.MONTH, month1);
		cal1.set(Calendar.DATE, day1);
		cal1.set(Calendar.YEAR, year1);
		cal1.set(Calendar.HOUR, hour1);
		cal1.set(Calendar.MINUTE, minute1);
		cal1.set(Calendar.AM_PM, amPm1);

		Calendar cal2 = Calendar.getInstance(timeZone, locale);

		cal2.set(Calendar.MONTH, month2);
		cal2.set(Calendar.DATE, day2);
		cal2.set(Calendar.YEAR, year2);
		cal2.set(Calendar.HOUR, hour2);
		cal2.set(Calendar.MINUTE, minute2);
		cal2.set(Calendar.AM_PM, amPm2);

		return cal1.after(cal2);
	}

	/**
	 * Checks if is broadcast date.
	 *
	 * @param month the month
	 * @param day   the day
	 * @param year  the year
	 * @return true, if is broadcast date
	 */
	public static boolean isBroadcastDate(int month, int day, int year) {
		if (!isDate(month, day, year)) {
			return false;
		}

		Calendar cal1 = Calendar.getInstance();

		cal1.setFirstDayOfWeek(Calendar.MONDAY);
		cal1.set(Calendar.MONTH, month);
		cal1.set(Calendar.DATE, day);
		cal1.set(Calendar.YEAR, year);

		Calendar cal2 = Calendar.getInstance();

		cal2.setFirstDayOfWeek(Calendar.MONDAY);
		cal2.set(Calendar.MONTH, month + 1);
		cal2.set(Calendar.DATE, 1);
		cal2.set(Calendar.YEAR, year);

		if (cal2.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY
				&& cal2.get(Calendar.WEEK_OF_YEAR) == cal1.get(Calendar.WEEK_OF_YEAR)) {

			return false;
		}

		return true;
	}

	/**
	 * Checks if is date.
	 *
	 * @param month the month
	 * @param day   the day
	 * @param year  the year
	 * @return true, if is date
	 */
	public static boolean isDate(int month, int day, int year) {
		return Validator.isDate(month, day, year);
	}

	// public static boolean isFuture(int month, int day, int year, int hour,
	// int minute, int amPm) {
	//
	// return isFuture(
	// month, day, year, hour, minute, amPm, TimeZoneUtil.getDefault(),
	// LocaleUtil.getDefault());
	/**
	 * Checks if is future.
	 *
	 * @param month    the month
	 * @param day      the day
	 * @param year     the year
	 * @param hour     the hour
	 * @param minute   the minute
	 * @param amPm     the am pm
	 * @param timeZone the time zone
	 * @param locale   the locale
	 * @return true, if is future
	 */
	// }
	public static boolean isFuture(int month, int day, int year, int hour, int minute, int amPm, TimeZone timeZone,
			Locale locale) {

		Calendar curCal = Calendar.getInstance(timeZone, locale);

		Calendar cal = (Calendar) curCal.clone();

		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.AM_PM, amPm);

		return cal.after(curCal);
	}

	// public static boolean isFuture(int month, int day, int year) {
	// return isFuture(
	// month, day, year, TimeZoneUtil.getDefault(),
	// LocaleUtil.getDefault());
	/**
	 * Checks if is future.
	 *
	 * @param month    the month
	 * @param day      the day
	 * @param year     the year
	 * @param timeZone the time zone
	 * @param locale   the locale
	 * @return true, if is future
	 */
	// }
	public static boolean isFuture(int month, int day, int year, TimeZone timeZone, Locale locale) {

		Calendar curCal = Calendar.getInstance(timeZone, locale);

		Calendar cal = (Calendar) curCal.clone();

		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		cal.set(Calendar.YEAR, year);

		return cal.after(curCal);
	}

	// public static boolean isFuture(int month, int year) {
	// return isFuture(
	// month, year, TimeZoneUtil.getDefault(), LocaleUtil.getDefault());
	/**
	 * Checks if is future.
	 *
	 * @param month    the month
	 * @param year     the year
	 * @param timeZone the time zone
	 * @param locale   the locale
	 * @return true, if is future
	 */
	// }
	public static boolean isFuture(int month, int year, TimeZone timeZone, Locale locale) {

		Calendar curCal = Calendar.getInstance(timeZone, locale);

		curCal.set(Calendar.DATE, 1);

		Calendar cal = (Calendar) curCal.clone();

		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.YEAR, year);

		return cal.after(curCal);
	}

	/**
	 * Checks if is gregorian date.
	 *
	 * @param month the month
	 * @param day   the day
	 * @param year  the year
	 * @return true, if is gregorian date
	 */
	public static boolean isGregorianDate(int month, int day, int year) {
		return Validator.isGregorianDate(month, day, year);
	}

	/**
	 * Checks if is julian date.
	 *
	 * @param month the month
	 * @param day   the day
	 * @param year  the year
	 * @return true, if is julian date
	 */
	public static boolean isJulianDate(int month, int day, int year) {
		return Validator.isJulianDate(month, day, year);
	}

	/**
	 * Round by minutes.
	 *
	 * @param cal      the cal
	 * @param interval the interval
	 * @return the calendar
	 */
	public static Calendar roundByMinutes(Calendar cal, int interval) {
		int minutes = cal.get(Calendar.MINUTE);

		int delta = 0;

		if (minutes < interval) {
			delta = interval - minutes;
		} else {
			delta = interval - minutes % interval;
		}

		if (delta == interval) {
			delta = 0;
		}

		cal.add(Calendar.MINUTE, delta);

		return cal;
	}

	/**
	 * Gets the time in millis.
	 *
	 * @param date the date
	 * @return the long
	 */
	private static long _getTimeInMillis(Date date) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		int hour = 0;
		int minute = 0;
		int second = 0;

		cal.set(year, month, day, hour, minute, second);

		long millis = cal.getTimeInMillis() / TimeUtil.DAY;

		return millis;
	}
}
