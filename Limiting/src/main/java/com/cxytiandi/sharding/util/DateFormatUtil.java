package com.cxytiandi.sharding.util;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/8/5
 * @Version 1.0.0
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class DateFormatUtil {
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal();

    public DateFormatUtil() {
    }

    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        return getSimpleDateFormat(pattern, Locale.getDefault());
    }

    public static String dateToStr(Date date, String format) {
        if (date == null) {
            date = new Date();
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static SimpleDateFormat getSimpleDateFormat(String pattern, Locale locale) {
        Map<String, SimpleDateFormat> formatMap = (Map)threadLocal.get();
        SimpleDateFormat format = null;
        if (formatMap == null) {
            formatMap = new HashMap();
            format = new SimpleDateFormat(pattern, locale);
            formatMap.put(pattern, format);
            threadLocal.set(formatMap);
        } else {
            format = (SimpleDateFormat)formatMap.get(pattern);
            if (format == null) {
                format = new SimpleDateFormat(pattern, locale);
                formatMap.put(pattern, format);
            }
        }

        return format;
    }

    public static Date parse(String pattern, String str) throws ParseException {
        return getSimpleDateFormat(pattern).parse(str);
    }

    public static Date parse(String str) throws ParseException {
        return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
    }

    public static Date parseNoError(String str) {
        try {
            return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
        } catch (ParseException var2) {
            return null;
        }
    }

    public static String format(Date date) {
        return getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String formatHideCurrentYearMonthDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(1);
        int day = c.get(6);
        Calendar c1 = Calendar.getInstance();
        int year1 = c1.get(1);
        int day1 = c1.get(6);
        if (year1 == year && day1 == day) {
            return getSimpleDateFormat("HH:mm:ss").format(date);
        } else {
            return year1 == year ? getSimpleDateFormat("MM-dd HH:mm:ss").format(date) : getSimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        }
    }

    public static long formatDay(long mss) {
        long days = mss / 86400000L;
        return days;
    }

    public static long formatHour(long mss) {
        long hours = mss / 1000L % 86400L / 3600L;
        return hours;
    }

    public static long formatMinute(long mss) {
        long minutes = mss / 1000L % 3600L / 60L;
        return minutes;
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException var4) {
            convertSuccess = false;
        }

        return convertSuccess;
    }

    public static String conversionTime(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        } else if (!NumberUtils.isNumber(time) || time.length() != 10 && time.length() != 13) {
            return !isValidDate(time) ? null : time;
        } else {
            long l = Long.parseLong(time);
            if (time.length() == 10) {
                l *= 1000L;
            }

            Date date = new Date(l);
            return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        }
    }

    public static void main(String[] args) {
        System.out.println(formatHideCurrentYearMonthDay(new Date()));
        System.out.println(formatHideCurrentYearMonthDay(new Date((new Date()).getTime() - 86400000L)));
        System.out.println(formatHideCurrentYearMonthDay(new Date((new Date()).getTime() - 43200000000L)));
    }
}

