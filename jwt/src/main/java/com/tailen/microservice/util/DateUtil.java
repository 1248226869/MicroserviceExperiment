package com.tailen.microservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-03
 */

public class DateUtil {
    private final static Logger log = LoggerFactory.getLogger(DateUtil.class);
    private final static ZoneId ZONE_ID = ZoneId.systemDefault();
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final static String DATE_FORMAT_SIMPLE = "yyyy-MM-dd";
    private static DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZONE_ID);
    private static DateTimeFormatter formatSimple = DateTimeFormatter.ofPattern(DATE_FORMAT_SIMPLE).withZone(ZONE_ID);


    private static long nday = 1000 * 24 * 60 * 60;
    private static long nhour = 1000 * 60 * 60;
    private static long nminute = 1000 * 60;

    public static Date dateFormatConversionForExtends(Date data, String sourceFormat, String targetFormat, String extendTime){
        SimpleDateFormat formatter = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat dFormat = new SimpleDateFormat(targetFormat);
        StringBuilder time = new StringBuilder();
        time.append(formatter.format(data));
        time.append(" ");
        time.append(extendTime);
        try {
            return dFormat.parse(time.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将时间转换为规定格式的字符串
     *
     * @param date
     * @return
     */
    public static String getStringByDateAndFormat(Date date,String formatString) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatString).withZone(ZONE_ID);
        return date != null ? dateTimeFormatter.format(date.toInstant()) : null;
    }

    /**
     * JDK8日期转换 instant->string
     *
     * @param instant
     * @return
     */
    public static String conventDateStrByDate(Instant instant) {
        Date date = Date.from(instant);
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String dateStr = format.format(date);
        return dateStr;
    }

    public static ZonedDateTime convertDateStrToZdt(String dateStr) {
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZONE_ID);
        ZonedDateTime dtDateTime = ZonedDateTime.parse(dateStr, dtFormatter);
        return dtDateTime.withZoneSameInstant(ZONE_ID);
    }


    /**
     * 将时间转换为规定格式的字符串
     *
     * @param date
     * @return
     */
    public static String getStringByDate(Date date) {
        return date != null ? format.format(date.toInstant()) : null;
    }


    /**
     * 将时间转换为规定格式的字符串
     *
     * @param date
     * @return
     */
    public static String getStringByZonedDateTime(ZonedDateTime date) {
        return date != null ? format.format(date.toInstant()) : null;
    }
    /**
     * 将时间转换为规定格式的字符串
     *
     * @param date
     * @return
     */
    public static String getStringByZonedDateTimeSimple(ZonedDateTime date) {
        return date != null ? formatSimple.format(date.toInstant()) : null;
    }





    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException calendar 对日期进行时间操作
     *                        getTimeInMillis() 获取日期的毫秒显示形式
     */
    public static int daysBetweenDate(Date smdate, Date bdate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串日期格式的计算
     *
     * @param sdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetweenStringDate(String sdate, String bdate) {
        long between_days = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(sdf.parse(sdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            log.error("执行DateUtil#daysBetweenStringDate方法异常,解析字符串出错", e);
        }

        return Integer.parseInt(String.valueOf(between_days));
    }



    public static String getSimpleStringByDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_SIMPLE);
        return date != null ? sdf.format(date) : null;
    }


    public static long minutesBetweenDate(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        return diff / 60000;
    }
    /**
     * 返回一定时间后的日期
     * @param date 开始计时的时间
     * @param year 增加的年
     * @param month 增加的月
     * @param day 增加的日
     * @param hour 增加的小时
     * @param minute 增加的分钟
     * @param second 增加的秒
     * @return
     */
    public  static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second){
        if(date == null){
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if(year != 0){
            cal.add(Calendar.YEAR, year);
        }
        if(month != 0){
            cal.add(Calendar.MONTH, month);
        }
        if(day != 0){
            cal.add(Calendar.DATE, day);
        }
        if(hour != 0){
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if(minute != 0){
            cal.add(Calendar.MINUTE, minute);
        }
        if(second != 0){
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }
}
