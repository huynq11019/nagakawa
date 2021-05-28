package com.nagakawa.guarantee.util;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtils {
    private static Logger log = LoggerFactory.getLogger(DateUtils.class);
    public final static long MS = 1;

    public final static long SECOND_MS = MS * 1000;

    public final static long MINUTE_MS = SECOND_MS * 60;

    public final static long HOUR_MS = MINUTE_MS * 60;

    public final static long DAY_MS = HOUR_MS * 24;

    public final static String NORM_DATE_PATTERN = "yyyy-MM-dd";

    public final static String NORM_2_DATE_PATTERN = "yyyy/MM/dd";

    public final static String NORM_TIME_PATTERN = "HH:mm:ss";

    public final static String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static String NORM_2_DATETIME_PATTERN = "yyyy-MM-dd HH:mm";

    public final static String NORM_3_DATETIME_PATTERN = "dd/MM/yyyy HH:mm";

    public final static String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    public final static String NORM_3_DATE_PATTERN = "dd/MM/yyyy";

    public final static String NORM_2_TIME_PATTERN = "HH:mm";

    public static final String ISO_8601_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";
    
	public static final String LONG_DATE_PATTERN = "dd/MM/yyyy HH:mm:ss";
	
    
    public static Date atEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static Date atStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static int compareTo(Date date1, Date date2) {
		return compareTo(date1, date2, false);
	}

    public static int compareTo(Date date1, Date date2, boolean ignoreMilliseconds) {

		// Workaround for bug in JDK 1.5.x. This bug is fixed in JDK 1.5.07. See
		// http://bugs.sun.com/bugdatabase/view_bug.do;:YfiG?bug_id=6207898 for
		// more information.

		if ((date1 != null) && (date2 == null)) {
			return -1;
		} else if ((date1 == null) && (date2 != null)) {
			return 1;
		} else if ((date1 == null) && (date2 == null)) {
			return 0;
		}

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		if (ignoreMilliseconds) {
			time1 = time1 / TimeUtil.SECOND;
			time2 = time2 / TimeUtil.SECOND;
		}

		if (time1 == time2) {
			return 0;
		} else if (time1 < time2) {
			return -1;
		} else {
			return 1;
		}
	}

    public static String convertFromInstantToHour(Instant instant) {
        Date myDate = Date.from(instant);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String formattedDate = formatter.format(myDate);
        return formattedDate;
    }

    public static String convertFromInstantToHour2(Instant instant) {
        Date myDate = Date.from(instant.plus(7, ChronoUnit.HOURS));
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String formattedDate = formatter.format(myDate);
        return formattedDate;
    }

    public static String convertFromInstantToString(Instant instant) {
        Date myDate = Date.from(instant.plus(7, ChronoUnit.HOURS));
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(myDate);
        return formattedDate;
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static long diff(Date subtrahend, Date minuend, long diffField) {
        long diff = minuend.getTime() - subtrahend.getTime();
        return diff / diffField;
    }

    public static boolean equals(Date date1, Date date2) {
		if (compareTo(date1, date2) == 0) {
			return true;
		} else {
			return false;
		}
	}

    public static boolean equals(Date date1, Date date2, boolean ignoreMilliseconds) {

		if (!ignoreMilliseconds) {
			return equals(date1, date2);
		}

		long deltaTime = date1.getTime() - date2.getTime();

		if ((deltaTime > -1000) && (deltaTime < 1000)) {
			return true;
		} else {
			return false;
		}
	}

    private static String findDayOfWeek(DayOfWeek dayOfWeek) {
		if (Calendar.MONDAY == dayOfWeek.getValue()) {
			return "Thứ 2";
		} else if (Calendar.TUESDAY == dayOfWeek.getValue()) {
			return "Thứ 3";
		} else if (Calendar.WEDNESDAY == dayOfWeek.getValue()) {
			return "Thứ 4";
		} else if (Calendar.THURSDAY == dayOfWeek.getValue()) {
			return "Thứ 5";
		} else if (Calendar.FRIDAY == dayOfWeek.getValue()) {
			return "Thứ 6";
		} else if (Calendar.SATURDAY == dayOfWeek.getValue()) {
			return "Thứ 7";
		} else if (Calendar.SUNDAY == dayOfWeek.getValue()) {
			return "Chủ nhật";
		} else {
			return StringPool.BLANK;
		}
	}

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(NORM_DATE_PATTERN).format(date);
    }

    public static Date formatDateDate(Date date) {
		Date dt;
		try {
			if (date != null) {
				SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
				String strd = formater.format(date);
				dt = formater.parse(strd);
				return dt;
			}
		} catch (ParseException ex) {
			return null;
		}
		return null;

	}

    public static String formatDateString(Date date) {
		if (date != null) {
			SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
			return formater.format(date);
		} else {
			return "";
		}

	}

    public static String formatDateTime(Date date) {
        return new SimpleDateFormat(NORM_DATETIME_PATTERN).format(date);
    }

    public static String formatHttpDate(Date date) {
        return new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.US).format(date);
    }

    public static String formatInstantAsString(Instant date, String pattern) {
        DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(pattern)
                .withZone(getZoneHCM());
        
        return DATE_TIME_FORMATTER.format(date);
    }

    public static String formatLongDate(Date date) {
		return getDate(date, LONG_DATE_PATTERN);
	}

    public static String formatShortDate(Date date) {
		return getDate(date, NORM_3_DATE_PATTERN);
	}

    public static String friendlyDateFormat(Instant startTime) {
        // TODO: Ngày kia
        ZonedDateTime startDateTime = ZonedDateTime.ofInstant(startTime, getZoneHCM());
        LocalDate today = LocalDate.now(getZoneHCM());
        LocalDate ofStartDate = startDateTime.toLocalDate();

        DayOfWeek dayOfWeekToday = today.getDayOfWeek();
        LocalDate firstDayOfWeek;
        LocalDate lastDayOfWeek;

        if (dayOfWeekToday.getValue() == 1) {
            firstDayOfWeek = today;
            lastDayOfWeek = today.plus(6, ChronoUnit.DAYS);
        } else if (dayOfWeekToday.getValue() == 7) {
            firstDayOfWeek = today.minus(dayOfWeekToday.getValue() - 6, ChronoUnit.DAYS);
            lastDayOfWeek = today;
        } else {
            firstDayOfWeek = today.minus(dayOfWeekToday.getValue() - 1, ChronoUnit.DAYS);
            lastDayOfWeek = today.plus(7 - dayOfWeekToday.getValue(), ChronoUnit.DAYS);
        }

        if ((firstDayOfWeek.isBefore(ofStartDate) && ofStartDate.isBefore(lastDayOfWeek)) || ofStartDate.equals(firstDayOfWeek) || ofStartDate.equals(lastDayOfWeek)) { // Trong tuần
            Integer betweenDays = ofStartDate.compareTo(today);
            if (betweenDays.equals(0)) {
                return "Hôm nay";
            } else if (betweenDays.equals(1)) {
                return "Ngày mai";
            } else if (betweenDays.equals(2)) {
                return "Ngày kia";
            } else if (betweenDays.equals(-1)) {
                return "Hôm qua";
            } else if (betweenDays.equals(-2)) {
                return "Hôm kia";
            } else {
                DayOfWeek dayOfWeek = ofStartDate.getDayOfWeek();
                String thisWeek = " Tuần này";
                return findDayOfWeek(dayOfWeek) + thisWeek;
            }
        } else {
            LocalDate firstDayNextWeek = firstDayOfWeek.plus(7, ChronoUnit.DAYS);
            LocalDate lastDayNextWeek = lastDayOfWeek.plus(7, ChronoUnit.DAYS);
            DayOfWeek dayOfWeek = ofStartDate.getDayOfWeek();
            if ((firstDayNextWeek.isBefore(ofStartDate) && ofStartDate.isBefore(lastDayNextWeek)) || ofStartDate.equals(firstDayNextWeek) || ofStartDate.equals(lastDayNextWeek)) {
                String nextWeek = " Tuần sau";
                return findDayOfWeek(dayOfWeek) + nextWeek;
            } else {
                return findDayOfWeek(dayOfWeek);
            }
        }
    }

    public static String friendlyDateTimeFormat7 (Instant instant) {
        String time = "";
        Instant now = Instant.now();
        Long second = ChronoUnit.SECONDS.between(instant, now);

        if (second < 60) {
            time = second + " giây trước"; // Giây
        } else {
            if (second >= 60 && second < 3600) {
                time = second/60 + " phút trước"; // Phút
            } else if (second >= 3600 && second < 3600 * 24) {
                time = second/3600 + " giờ trước"; // Giờ
            } else if (second >= 3600 * 24 && second < 3600 * 24 * 2) {
                time = "Hôm qua"; // Ngày hôm qua
            } else if (second >= 3600 * 24 * 2 && second < 3600 * 24 * 7) {
                time = second / (3600 * 24) + " ngày trước"; // Trong tuần
            } else if (second >= 3600 * 24 * 7 && second < 3600 * 24 * 7 * 4) {
                time = second / (3600 * 24 * 7) + " tuần trước"; // Trong tháng
            } else {
                // Trong năm
                Long day = ChronoUnit.DAYS.between(instant, now);
                if (day < 30) {
                    time = day/7 + " tuần trước"; // Trong tháng
                } else if (day >= 30 && day < 365) {
                    time = day/30 + " tháng trước";
                } else if (day >= 365 && day < 365 * 2) {
                    time = "Năm ngoái"; // Năm ngoái
                } else {
                    time = day/365 + " năm trước"; // Mấy năm trước
                }
            }
        }
        return time;
    }

    public static String friendlyFormat(Instant instant) {
        // TODO: 10h15’ Ngày kia (08/03)
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, getZoneHCM());
        String hour = modifyAddLeadingZeros(zonedDateTime.getHour());
        String minutes = modifyAddLeadingZeros(zonedDateTime.getMinute());
        String day = modifyAddLeadingZeros(zonedDateTime.getDayOfMonth());
        String month = modifyAddLeadingZeros(zonedDateTime.getMonthValue());
        String timeFormat = hour + "h" + minutes + "' ";
        String dateFormat = " (" + day + "/" +  month + ")";
        return  timeFormat + friendlyDateFormat(instant) + dateFormat;
    }

    public static String friendlyTimeOfDayFormat(Instant startTime) {
        ZonedDateTime ofStartZoned = startTime.atZone(ZoneId.of("Asia/Ho_Chi_Minh"));
        LocalTime localTime = ofStartZoned.toLocalTime();
        if (localTime.isBefore(LocalTime.of(12, 0, 0))) {
            return "Buổi sáng";
        }
        return "Buổi chiều";
    }

    public static String getCurrentDate(String pattern, Locale locale) {
		return getDate(new Date(), pattern, locale);
	}

    public static String getCurrentDate(String pattern, Locale locale, TimeZone timeZone) {

		return getDate(new Date(), pattern, locale, timeZone);
	}

    public static String getDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern);

		return dateFormat.format(date);
	}

    public static String getDate(Date date, String pattern, Locale locale) {
		if (date == null) {
			return null;
		}

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern, locale);

		return dateFormat.format(date);
	}

    public static String getDate(Date date, String pattern, Locale locale, TimeZone timeZone) {
		if (date == null) {
			return null;
		}

		Format dateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(pattern, locale, timeZone);

		return dateFormat.format(date);
	}

    public static Date getDateAfter(Date date, int afterDay) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_YEAR, afterDay);

		return cal.getTime();
	}

    public static Date getDateAfter(int afterDay) {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DAY_OF_YEAR, afterDay);

		return cal.getTime();
	}

    public static Date getDateBefore(Date date, int beforeDay) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(date);

		cal.add(Calendar.DAY_OF_YEAR, -beforeDay);

		return cal.getTime();
	}

    public static Date getDateBefore(int beforeDay) {
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DAY_OF_YEAR, -beforeDay);

		return cal.getTime();
	}

    public static Integer getDayCount(String start, String end) {
        Integer diff = -1;
        try {
            Date dateStart = parseDate(start);
            Date dateEnd = parseDate(end);

            //time is always 00:00:00, so rounding should help to ignore the missing hour when going from winter to summer time, as well as the extra hour in the other direction
            diff = Math.toIntExact(Math.round((dateEnd.getTime() - dateStart.getTime()) / (double) 86400000));
        } catch (Exception e) {
            //handle the exception according to your own situation
        }
        return diff + 1;
    }

    public static int getDaysBetween(Date startDate, Date endDate, TimeZone timeZone) {

		int offset = timeZone.getRawOffset();

		Calendar startCal = Calendar.getInstance(timeZone);

		startCal.setTime(startDate);
		startCal.add(Calendar.MILLISECOND, offset);

		Calendar endCal = Calendar.getInstance(timeZone);

		endCal.setTime(endDate);
		endCal.add(Calendar.MILLISECOND, offset);

		int daysBetween = 0;

		while (CalendarUtil.beforeByDay(startCal.getTime(), endCal.getTime())) {
			startCal.add(Calendar.DAY_OF_MONTH, 1);

			daysBetween++;
		}

		return daysBetween;
	}

    public static Date getFirstDayOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        return DateUtils.atStartOfDay(calendar.getTime());
    }

    public static Date getFirstDayOfCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        return DateUtils.atStartOfDay(calendar.getTime());
    }

    public static String getFriendlyTimeFormat(Instant start, Instant end) {
        return LocalTime.from(start.atZone(getZoneHCM()))
                + " - " + LocalTime.from(end.atZone(getZoneHCM()));
    }

    public static DateFormat getISO8601Format() {
		return DateFormatFactoryUtil.getSimpleDateFormat(ISO_8601_PATTERN);
	}

    public static DateFormat getISOFormat() {
		return getISOFormat(StringPool.BLANK);
	}

    public static DateFormat getISOFormat(String text) {
		String pattern = StringPool.BLANK;

		if (text.length() == 8) {
			pattern = "yyyyMMdd";
		} else if (text.length() == 12) {
			pattern = "yyyyMMddHHmm";
		} else if (text.length() == 13) {
			pattern = "yyyyMMdd'T'HHmm";
		} else if (text.length() == 14) {
			pattern = "yyyyMMddHHmmss";
		} else if (text.length() == 15) {
			pattern = "yyyyMMdd'T'HHmmss";
		} else if ((text.length() > 8) && (text.charAt(8) == 'T')) {
			pattern = "yyyyMMdd'T'HHmmssz";
		} else {
			pattern = "yyyyMMddHHmmssz";
		}

		return DateFormatFactoryUtil.getSimpleDateFormat(pattern);
	}

    public static Date getNextBirthDay(Date birthDay) {
		Calendar calBd = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();

		calBd.setTime(birthDay);

		cal.set(Calendar.MONTH, calBd.get(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, calBd.get(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

    public static Instant getTimeFinish(String dateText) {
        try {
            LocalDateTime date = LocalDateTime.parse(dateText + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return date.toInstant(ZoneOffset.ofHours(+7));
        } catch (DateTimeParseException e) {
        }
        return null;
    }

	public static Instant getTimeStart(String dateText) {
        try {
            LocalDateTime date = LocalDateTime.parse(dateText + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            return date.toInstant(ZoneOffset.ofHours(+7));
        } catch (DateTimeParseException e) {
        }
        return null;
    }

    public static DateFormat getUTCFormat() {
		return getUTCFormat(StringPool.BLANK);
	}
    
    public static DateFormat getUTCFormat(String text) {
		String pattern = StringPool.BLANK;

		if (text.length() == 8) {
			pattern = "yyyyMMdd";
		} else if (text.length() == 12) {
			pattern = "yyyyMMddHHmm";
		} else if (text.length() == 13) {
			pattern = "yyyyMMdd'T'HHmm";
		} else if (text.length() == 14) {
			pattern = "yyyyMMddHHmmss";
		} else if (text.length() == 15) {
			pattern = "yyyyMMdd'T'HHmmss";
		} else {
			pattern = "yyyyMMdd'T'HHmmssz";
		}

		return DateFormatFactoryUtil.getSimpleDateFormat(pattern);
	}

	public static ZoneId getZoneHCM() {
        return ZoneId.of("Asia/Ho_Chi_Minh");
    }

	public static Date lastMouth() {
        return offsiteDate(new Date(), Calendar.MONTH, -1);
    }

	public static Date lastWeek() {
        return offsiteDate(new Date(), Calendar.WEEK_OF_YEAR, -1);
    }

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

	private static String modifyAddLeadingZeros(int number) {
        if (number > 9) {
            return String.valueOf(number);
        }
        return "0" + number;
    }

	public static Date newDate() {
		return new Date();
	}

	public static Date newDate(long date) {
		return new Date(date);
	}

	public static long newTime() {
		Date date = new Date();

		return date.getTime();
	}

	public static String now() {
        return formatDateTime(new Date());
    }

	public static Instant nowInstant() {
        Instant instant = Instant.now(Clock.system(getZoneHCM()));
        return instant;
    }

	public static Date offsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }

	public static Date parse(String dateStr) {
        int length = dateStr.length();
        try {
            if (length == DateUtils.NORM_DATETIME_PATTERN.length()) {
                return parseDateTime(dateStr);
            } else if (length == DateUtils.NORM_DATE_PATTERN.length()) {
                return parseDate(dateStr);
            } else if (length == DateUtils.NORM_TIME_PATTERN.length()) {
                return parseTime(dateStr);
            }
        } catch (Exception e) {
            log.error("Parse " + dateStr + " with format normal error!", e);
        }
        return null;
    }

	public static Date parse(String dateString, String format) {
        try {
            return (new SimpleDateFormat(format)).parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + format + " error!", e);
        }
        return null;
    }

	public static Date parseDate(String dateString) {
        try {
            return new SimpleDateFormat(NORM_DATE_PATTERN).parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + NORM_DATE_PATTERN + " or" + NORM_2_DATE_PATTERN + " error!", e);
        }

        try {
            return new SimpleDateFormat(NORM_2_DATE_PATTERN).parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + NORM_2_DATE_PATTERN + " error!", e);
        }
        return null;
    }

	public static Date parseDateTime(String dateString) {
        try {
            return new SimpleDateFormat(NORM_DATETIME_PATTERN).parse(dateString);
        } catch (ParseException e) {
            log.error("Parse " + dateString + " with format " + new SimpleDateFormat(NORM_DATETIME_PATTERN).toPattern() + " error!", e);
        }
        return null;
    }

	public static Instant parseEndOfDay(String dateStr) {
        Date date = parse(dateStr);
        if (date != null) {
            date = atEndOfDay(date);
            return dateToLocalDateTime(date).toInstant(ZoneOffset.ofHours(+7));
        }
        return null;
    }

	public static Instant parseStartOfDay(String dateStr) {
        Date date = parse(dateStr);
        if (date != null) {
            date = atStartOfDay(date);
            return dateToLocalDateTime(date).toInstant(ZoneOffset.ofHours(+7));
        }
        return null;
    }

	public static Instant parseStartOfDay2(String dateStr) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStr);
            if (date != null) {
                date = atStartOfDay(date);
                return dateToLocalDateTime(date).toInstant(ZoneOffset.ofHours(+7));
            }
        } catch (ParseException e) {
        }
        return null;
    }

	public static Date parseTime(String timeString) {
        try {
            return new SimpleDateFormat(NORM_TIME_PATTERN).parse(timeString);
        } catch (ParseException e) {
            log.error("Parse " + timeString + " with format " + NORM_TIME_PATTERN + " error!", e);
        }
        return null;
    }

	public static Instant parseToInstant(String dateStr, String format) {
        Date date;
        try {
            date = new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }

        return date.toInstant();
    }

	public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }

	public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

	public static boolean timestampInRange(Instant startTime, Instant endTime, Instant currentTime) {
        return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
    }

	public static boolean timestampInRange(Instant startTimeConfig, Instant endTimeConfig, Instant startTime, Instant endTime) {
        return (startTimeConfig.isBefore(startTime) && endTimeConfig.isAfter(endTime)) || (startTimeConfig.equals(startTime) || endTime.equals(endTimeConfig));
    }

	public static String today() {
        return formatDate(new Date());
    }

	public static Date yesterday() {
        return offsiteDate(new Date(), Calendar.DAY_OF_YEAR, -1);
    }
}
