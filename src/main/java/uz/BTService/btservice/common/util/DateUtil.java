package uz.BTService.btservice.common.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class DateUtil {
    public static String PATTERN1 = "yyyy-MM-dd HH:mm:ss";
    public static String PATTERN2 = "yyyy-MM-dd";
    public static String PATTERN3 = "dd-MM-yyyy";
    public static String PATTERN4 = "dd-MMMM";
    public static String PATTERN5 = "HH:mm";
    public static String PATTERN6 = "yyyy/MM/dd";
    public static String PATTERN7 = "yyyyMMddHHmmss";
    public static String PATTERN8 = "dd-MM-yyyy HH:mm";
    public static String PATTERN9 = "yyyyMMddHHmmssSSS";
    public static String PATTERN10 = "ddMMyyyyHHmmss";
    public static String PATTERN11 = "dd-MM-yy HH:mm";
    public static String PATTERN12 = "dd.MM.yyyy HH:mm:ss";
    public static String PATTERN13 = "dd.MM.yyyy HH:mm:ss.SSS";

    public static String PATTERN14 = "dd.MM.yyyy";

    public DateUtil() {
    }

    public static String format(Date date, String pattern) {
        return date == null ? null : (new SimpleDateFormat(pattern)).format(date);
    }

    public static String format(Date date, String pattern, Locale locale) {
        return date == null ? null : (new SimpleDateFormat(pattern, locale)).format(date);
    }

    public static Date parse(String value, String pattern) {
        if (value == null) {
            return null;
        } else {
            try {
                return (new SimpleDateFormat(pattern)).parse(value);
            } catch (ParseException var3) {
                return null;
            }
        }
    }

    public static LocalDate toLocale(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    public static LocalDate toLocaleOrNow(Date date) {
        return date != null ? date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : LocalDate.now();
    }

    public static Date fromLocale(LocalDate date) {
        if (date != null) {
            Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
            return Date.from(instant);
        } else {
            return null;
        }
    }

    public static Date fromLocaleOrNow(LocalDate date) {
        if (date != null) {
            Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
            return Date.from(instant);
        } else {
            return new Date();
        }
    }

    public static boolean isValid(String value, String pattern) {
        try {
            (new SimpleDateFormat(pattern)).parse(value);
            return true;
        } catch (ParseException var3) {
            return false;
        }
    }

    public static Date atStartOfDay(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            return calendar.getTime();
        }
    }

    public static Date atEndOfDay(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            calendar.set(14, 999);
            return calendar.getTime();
        }
    }

    public static Date atStartOfMonth(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.set(5, 1);
            return calendar.getTime();
        }
    }

    public static Date atEndOfMonth(Date date) {
        if (date == null) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(11, 23);
            calendar.set(12, 59);
            calendar.set(13, 59);
            calendar.set(14, 999);
            calendar.set(5, calendar.getActualMaximum(5));
            return calendar.getTime();
        }
    }

    public static Date addMonths(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, months);
        return calendar.getTime();
    }

    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, days);
        return calendar.getTime();
    }

    public static Date addSeconds(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(13, seconds);
        return calendar.getTime();
    }

    public static int dayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(7);
    }

    public static Date today() {
        Date now = new Date();
        return new Date(now.getDate(), now.getMonth(), now.getDate());
    }

    public static String[] weekdays() {
        Locale locale = Locale.ENGLISH;
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        return dfs.getWeekdays();
    }

    public static Map<Integer, String> weekdaysAsMap() {
        Map<Integer, String> result = new TreeMap();
        String[] weekdays = weekdays();

        for(int i = 0; i < weekdays.length; ++i) {
            result.put(i, weekdays[i]);
        }

        return result;
    }

    public static Map<Integer, String> weekdaysAsMap(int startDayOfWeek) {
        Map<Integer, String> result = new LinkedHashMap();
        String[] weekdays = weekdays();

        int i;
        for(i = startDayOfWeek; i < weekdays.length; ++i) {
            result.put(i, weekdays[i]);
        }

        for(i = 0; i < startDayOfWeek; ++i) {
            result.put(i, weekdays[i]);
        }

        return result;
    }

    public static Map<Integer, Map<String, Object>> weekdaysAsMap(int startDayOfWeek, Locale locale) {
        Map<Integer, Map<String, Object>> result = new LinkedHashMap();
        String[] weekdays = weekdays();

        int i;
        HashMap weekday;
        for(i = startDayOfWeek; i < weekdays.length; ++i) {
            weekday = new HashMap();
            weekday.put("name", weekdays[i]);
            weekday.put("date", format(futureFirstDay(i), PATTERN4, locale));
            result.put(i, weekday);
        }

        for(i = 0; i < startDayOfWeek; ++i) {
            weekday = new HashMap();
            weekday.put("name", weekdays[i]);
            weekday.put("date", format(futureFirstDay(i), PATTERN4, locale));
            result.put(i, weekday);
        }

        return result;
    }

    public static Date futureFirstDay(int dayOfWeek) {
        Date today = new Date();
        if (dayOfWeek <= 0) {
            return today;
        } else {
            int currentDayOfWeek = dayOfWeek(today);
            return dayOfWeek >= currentDayOfWeek ? addDays(today, dayOfWeek - currentDayOfWeek) : addDays(today, 7 + dayOfWeek - currentDayOfWeek);
        }
    }

    public static int monthsBetweenDates(Date d1, Date d2) {
        if (d1 != null && d2 != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d1);
            int nMonth1 = 12 * calendar.get(1) + calendar.get(2);
            calendar.setTime(d2);
            int nMonth2 = 12 * calendar.get(1) + calendar.get(2);
            return Math.abs(nMonth2 - nMonth1);
        } else {
            return -1;
        }
    }
    public static int getField(Date date, int field) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(field);
    }

    public static int getHour(Date date) {
        return getField(date, 11);
    }

    public static int getMinute(Date date) {
        return getField(date, 12);
    }

    public static int getSecond(Date date) {
        return getField(date, 13);
    }

    public static List<Date> getDatesBetween(Date from, Date to) {
        List<Date> datesInRange = new ArrayList();
        if (from != null && to != null) {
            Calendar fromCal = Calendar.getInstance();
            fromCal.setTime(from);

            while(fromCal.getTime().before(to)) {
                datesInRange.add(fromCal.getTime());
                fromCal.add(5, 1);
            }

            return datesInRange;
        } else {
            return datesInRange;
        }
    }

    public static long daysBetween(Date a, Date b) {
        return TimeUnit.DAYS.convert(atStartOfDay(a).getTime() - atStartOfDay(b).getTime(), TimeUnit.MILLISECONDS);
    }
}
