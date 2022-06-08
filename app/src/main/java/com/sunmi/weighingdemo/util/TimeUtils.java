package com.sunmi.weighingdemo.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


public class TimeUtils {

    public static final String FORMAT_TIME = "yyyy-MM-dd";
    public static final String FORMAT_TIME_DAY = "yyyy年MM月dd日";
    public static final String FORMAT_TIME_DAY_2 = "yyyy年M月d日";
    public static final String FORMAT_TIME_DAY_NO_SECOND = "yyyy年MM月dd日";
    public static final String FORMAT_TIME_DAY_HM = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_TIME_STATEMENT = "yyyy年M月";
    public static final String FORMAT_TIME_ALL = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_HOUR_MINIUTE_1 = "HH:mm";
    public static final String FORMAT_TIME_NO_SECOND = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_HOUR_MINITE_2 = "H时mm分";
    public static final String FORMAT_YEAR_MONTH_DAY_1 = "yyyy-MM-dd";
    public static final String FORMAT_YEAR_MONTH_DAY_2 = "yyyy/MM/dd";
    public static final String FORMAT_MONTH = "MM-dd HH:mm";
    public static final String FORMAT_MONTH_DAY = "MM/dd";
    public static final String FORMAT_MONTH_DAY_1 = "MM月dd日";
    public static final String FORMAT_MONTH_DAY_2 = "MM-dd";
    public static final String FORMAT_HOUR = "HH:mm:ss";
    public static final String FORMAT_YEAR = "yyyy-MM-dd";
    public static final String FORMAT_ORDER = "yyyyMMdd";

    private static ThreadLocal<SimpleDateFormat> tl = new ThreadLocal<>();

    private static SimpleDateFormat getFormat(String pattern) {
        SimpleDateFormat sdf = tl.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(pattern, Locale.getDefault());
            tl.set(sdf);
        } else {
            sdf.applyPattern(pattern);
        }
        return sdf;
    }

    public static String getDay(long millis) {
        return formatDate(millis * 1000, FORMAT_TIME_DAY);
    }

    public static String getDay22(long millis) {
        return formatDate(millis, FORMAT_TIME_DAY_2);
    }

    public static String getDay(long millis, String format) {
        return formatDate(millis * 1000, format);
    }

    public static String getNormalDate(long millis) {
        return formatDate(millis, FORMAT_YEAR_MONTH_DAY_1);
    }

    public static String getStatementDate(long millis) {
        return formatDate(millis, FORMAT_TIME_STATEMENT);
    }


    public static String getMonthDay(long millis) {
        return formatDate(millis * 1000, FORMAT_MONTH_DAY);
    }

    public static String getMonthDayOther(long millis) {
        return formatDate(millis, FORMAT_MONTH_DAY_1);
    }

    public static String getMonthDayOther2(long millis) {
        return formatDate(millis * 1000L, FORMAT_MONTH_DAY_2);
    }


    public static String getHour(long millis) {
        return formatDate(millis, FORMAT_HOUR);
    }

    public static String getMinute(long millis) {
        return formatDate(millis * 1000, FORMAT_HOUR_MINIUTE_1);
    }

    /**
     * 从yyyy-MM-dd HH:mm:ss格式时间中截取HH:mm
     *
     * @param date
     * @return
     */
    public static String getHourMin(String date) {
        if (TextUtils.isEmpty(date)) return "";
        String result = "";
        try {
            SimpleDateFormat df = getFormat(FORMAT_TIME_ALL);
            Date ori = df.parse(date);
            SimpleDateFormat df1 = getFormat(FORMAT_HOUR_MINIUTE_1);
            result = df1.format(ori);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getHourMin(long time) {
        SimpleDateFormat df = getFormat(FORMAT_HOUR_MINIUTE_1);
        return df.format(new Date(time));
    }

    /**
     * 获取时间毫秒值
     *
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static long getDateMills(String dateStr, SimpleDateFormat dateFormat) {
        long result = 0;
        try {
            Date ti = dateFormat.parse(dateStr);
            result = ti.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 从yyyy-MM-dd HH:mm:ss格式时间中截取yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String getTimeNoSecond(String date) {
        String result = "";
        try {
            SimpleDateFormat df = getFormat(FORMAT_TIME_ALL);
            Date ori = df.parse(date);
            SimpleDateFormat df1 = getFormat(FORMAT_TIME_NO_SECOND);
            result = df1.format(ori);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取yyyy/MM/dd格式日期
     *
     * @param mills
     * @return
     */
    public static String getDay2(long mills) {
        String result = "";
        try {
            SimpleDateFormat df = getFormat(FORMAT_YEAR_MONTH_DAY_2);
            result = df.format(new Date(mills * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getMonth(long timeInMillis) {
        return formatDate(timeInMillis * 1000, FORMAT_MONTH);
    }

    public static String getDate(long timeInMillis) {
        return formatDate(timeInMillis, FORMAT_TIME);
    }

    public static String getDateNoSecondFromSecond(long timeInSecond) {
        return formatDate(timeInSecond * 1000, FORMAT_TIME);
    }

    public static String getDateNoSecondFromSecond(long timeInSecond, String format) {
        return formatDate(timeInSecond * 1000, format);
    }

    public static String formatDate(long timeInMillis, String pattern) {
        return formatDate(new Date(timeInMillis), pattern);
    }

    public static String formatDate(Calendar calendar, String pattern) {
        return formatDate(calendar.getTime(), pattern);
    }

    public static String formatDate(Date date, String pattern) {
        return formatDate(date, pattern, null);
    }

    public static String formatDate(Date date, String pattern, Locale locale) {
        SimpleDateFormat simpleDateFormat;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        } else {
            simpleDateFormat = new SimpleDateFormat(pattern, locale);
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 时间转为时间戳
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return ms
     */
    public static long dateToStamp(String date) {
        long result = 0;
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_TIME_ALL, Locale.getDefault());
        try {
            Date ti = df.parse(date);
            result = ti.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 计算两个时间 相隔 以 00:15
     *
     * @param time ms
     * @return
     */
    public static String timeToHourAndMin(long time) {
        int hour = 0;
        int min = 0;
        if (time / 3600 >= 1) {
            hour = (int) (time / 3600);
        }
        time = time - 3600 * hour;
        if (time / 60 >= 1) {
            min = (int) (time / 60);
        }
        String str = "";
        if (hour > 9) {
            str = hour + ":";
        } else {
            str = "0" + hour + ":";
        }
        str += min;
        return str;
    }

    /**
     * 毫秒显示 小时:分钟:秒
     *
     * @param time ms
     * @return
     */
    public static String timeToHourAndMinAndSecond(long time) {
        time = Math.abs(time) / 1000;
        int hour = 0;
        int min = 0;
        int second = 0;
        hour = (int) (time / 3600);
        time = time - 3600 * hour;
        min = (int) (time / 60);
        second = (int) (time - min * 60);
        String str = "";
        if (hour > 9) {
            str += hour + ":";
        } else {
            str += "0" + hour + ":";
        }
        if (min > 9) {
            str += min + ":";
        } else {
            str += "0" + min + ":";
        }
        if (second > 9) {
            str += second;
        } else {
            str += "0" + second;
        }
        return str;
    }

    /**
     * 判断给定日期是否为今天
     *
     * @param year  某年
     * @param month 某月
     * @param day   某天
     * @return ...
     */
    public static boolean isToday(int year, int month, int day) {
        Calendar c1 = Calendar.getInstance();
        c1.set(year, month - 1, day);
        return (c1.get(Calendar.YEAR) == year) &&
                (c1.get(Calendar.MONTH) == month - 1) &&
                (c1.get(Calendar.DAY_OF_MONTH) == day);
    }

    /**
     * 判断给定日期是否为今天
     *
     * @param calendar
     * @return
     */
    public static boolean isToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                && calendar.get(Calendar.DATE) == today.get(Calendar.DATE);
    }

    /**
     * 判断某年是否为闰年
     *
     * @param year ...
     * @return true表示闰年
     */
    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }

    /**
     * 是否是同一天
     *
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE);
    }

    /**
     * 是否是同一天
     *
     * @param time1
     * @param time2
     * @return
     */
    public static boolean isSameDay(long time1, long time2) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTimeInMillis(time1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(time2);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE);
    }

    /**
     * 判断某个日期是否在两个日期之间
     *
     * @param calendar
     * @param calendars
     * @return
     */
    public static boolean isDayBetweenTwoDate(Calendar calendar, Calendar[] calendars) {
        return calendar.getTimeInMillis() >= calendars[0].getTimeInMillis()
                && calendar.getTimeInMillis() < calendars[1].getTimeInMillis();
    }

    /**
     * 比较两个calendar
     *
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static int compareCalendar(Calendar calendar1, Calendar calendar2) {
        return compare(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());
    }

    /**
     * @param time1
     * @param time2
     * @return
     */
    public static int compare(long time1, long time2) {
        if (time1 < time2) {
            return -1;
        } else if (time1 == time2) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * 获取今天
     *
     * @return
     */
    public static Calendar getToday() {
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    /**
     * 获取今天
     *
     * @return
     */
    public static long getTodayInSecond() {
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE), 0, 0, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today.getTimeInMillis() / 1000;
    }

    /**
     * 获取明天
     *
     * @return
     */
    public static Calendar getTomorrow() {
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DATE) + 1, 0, 0, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    /**
     * @return 返回值描述
     * @throws <异常类型> {@inheritDoc} 异常描述
     * @author 方法描述:获取某天，-1是昨天，0是今天，1是明天依次类推
     * @param参数描述 格式M月dd日这样的话显示的就是3月12日，否则 MM月dd日格式会选择03月12日
     */

    public static Calendar getmoutianMD(int i) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, i);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;


    }

    /**
     * 日期字符串转换Date实体
     *
     * @param serverTime
     * @param format
     * @return
     */
    public static Date parseServerTime(String serverTime, String format) {
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 设置到周日
     *
     * @param calendar
     * @param isSundayFirday 是否以周日为周起始日
     */
    public static void setToSunday(Calendar calendar, boolean isSundayFirday) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        if (dayOfWeek == 0) {
            return;
        }
        if (!isSundayFirday) {
            dayOfWeek = dayOfWeek - 7;
        }
        calendar.add(Calendar.DATE, -dayOfWeek);
    }

    /**
     * 将Calendar设置到周一
     *
     * @param calendar
     * @param isSundayFirstDay 是否以周日为起始日
     */
    public static void setToMonday(Calendar calendar, boolean isSundayFirstDay) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
        if (dayOfWeek == 0) {
            return;
        }
        if (dayOfWeek == -1 && !isSundayFirstDay) {
            dayOfWeek = 6;
        }
        calendar.add(Calendar.DATE, -dayOfWeek);
    }

    /**
     * 获取周日
     *
     * @param isSundayFirstDay
     * @return
     */
    public static Calendar getSunday(boolean isSundayFirstDay) {
        Calendar today = Calendar.getInstance();
        setToSunday(today, isSundayFirstDay);
        today.set(0, 0, 0);
        today.setTimeInMillis(0);
        return today;
    }

    /**
     * 获取周一
     *
     * @return
     */
    public static Calendar getMonday(boolean isSundayFirstDay) {
        Calendar today = Calendar.getInstance();
        setToMonday(today, isSundayFirstDay);
        today.set(0, 0, 0);
        today.setTimeInMillis(0);
        return today;
    }


    /**
     * 获取今年第一天
     *
     * @return
     */
    public static Calendar getYearStart() {
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), 0, 1);
        today.set(0, 0, 0);
        today.setTimeInMillis(0);
        return today;
    }

    /**
     * 获取今年最后一天
     *
     * @return
     */
    public static Calendar getYearEnd() {
        Calendar today = Calendar.getInstance();
        today.set(today.get(Calendar.YEAR), 11, 31);
        today.set(0, 0, 0);
        today.setTimeInMillis(0);
        return today;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static Calendar getMonthStart() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DATE, 1);
        today.set(0, 0, 0);
        today.setTimeInMillis(0);
        return today;
    }

    /**
     * 获取当月最后一天
     *
     * @return
     */
    public static Calendar getMonthEnd() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.DATE, getDaysInMonth(today));
        today.set(0, 0, 0);
        today.setTimeInMillis(0);
        return today;
    }

    public static int getDaysInMonth(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DATE);
    }

    private static int[] DAYSINMONTH;

    /**
     * @param year  年份
     * @param month 月份，真实月份减一
     * @return
     */
    public static int getDaysInMonth(int year, int month) {
        if (DAYSINMONTH == null) {
            DAYSINMONTH = new int[]{
                    31, 28, 31, 30, 31,
                    30, 31, 31, 30, 31,
                    30, 31};
        }
        if (isLeapYear(year) && month == 1) {
            return 29;
        }
        int days = 0;
        try {
            days = DAYSINMONTH[month];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 通过毫秒数设置日期
     *
     * @param originCalendar 需要设置的
     * @param newCalendar    被copy的
     */
    public static void copyCalendar(Calendar originCalendar, Calendar newCalendar) {
        originCalendar.setTimeInMillis(newCalendar.getTimeInMillis());
    }

    /**
     * 拷贝一个Calendar
     *
     * @param calendar
     * @return
     */
    public static Calendar copyCalendar(Calendar calendar) {
        Calendar newCalendar = Calendar.getInstance();
        newCalendar.setTimeInMillis(calendar.getTimeInMillis());
        return newCalendar;
    }

    /**
     * 将Calendar的时分秒毫秒值设为0
     *
     * @param calendar
     */
    public static void resetCalendarHour(Calendar calendar) {
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 获取两个日期间隔的月数
     *
     * @param calendar1
     * @param calendar2
     * @return
     */
    public static int getMonthCountBetweenDate(Calendar calendar1, Calendar calendar2) {
        return (calendar1.get(Calendar.YEAR) - calendar2.get(Calendar.YEAR)) * 12 + (calendar1.get(Calendar.MONTH) - calendar2.get(Calendar.MONTH));
    }

    /**
     * 获取两个日期间隔的天数
     *
     * @param calendar1
     * @param calendar2
     * @return 有正负
     */
    public static int getDaysBetweenDate(Calendar calendar1, Calendar calendar2) {
        return getDaysBetweenDate(calendar1.getTimeInMillis(), calendar2.getTimeInMillis());
    }

    /**
     * 获取两个日期间隔的天数
     *
     * @param date1
     * @param date2
     * @return 有正负
     */
    public static int getDaysBetweenDate(long date1, long date2) {
        return (int) ((date1 - date2) / (1000 * 3600 * 24));
    }

    /**
     * 这个只是针对日历格子上相差的周数
     *
     * @param calendar1 前的日期
     * @param calendar2 后面的日期
     * @return
     */
    public static int getWeeksBetweenDate(Calendar calendar1, Calendar calendar2) {
        Calendar tmp1 = copyCalendar(calendar1);
        Calendar tmp2 = copyCalendar(calendar2);
        setToMonday(tmp1, false);
        setToMonday(tmp2, false);
        return getDaysBetweenDate(tmp1, tmp2) / 7;
    }


    /**
     * 获取小时格式时间间隔
     *
     * @param millis
     * @return
     */
    public static String getHourTimeInterval(long millis) {
        return getHour(getGMTime() - millis * 1000);
    }


    /**
     * 获取当前格林威治时间
     *
     * @return
     */
    public static long getGMTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
        String format = sdf.format(new Date());
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            return sdf1.parse(format).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 获取一天零点
     *
     * @param day 0:当日  1:明天  -1: 昨天
     * @return
     */
    public static long getDayZero(int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取一天23点
     *
     * @param day 0:当日  1:明天  -1: 昨天
     * @return
     */
    public static long getDayLater(int day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getDayZeroByCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static long getDayLaterByCalendar(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * 获取周一的时间戳
     *
     * @param week 0:本周  1:下周  -1: 上周
     * @return 毫秒
     */
    public static long getWeekMonday(int week) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        cal.add(Calendar.DATE, week * 7);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * 获取周日的时间戳
     *
     * @param week 0:本周  1:下周  -1: 上周
     * @return 毫秒
     */
    public static long getWeekSunday(int week) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day + 6);
        cal.add(Calendar.DATE, week * 7);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


    /**
     * 获取每月第一天
     *
     * @param month 0:本月  -1 上月  1下月
     * @return
     */
    public static long getMonthFirstDay(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取每月最后一天
     *
     * @param month 0:本月  -1 上月  1下月
     * @return
     */
    public static long getMonthLastDay(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    public static int getYearByMills(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.YEAR);
    }


    public static int getWeekByMillis(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }


    /**
     * 获取月最大天数
     *
     * @param month 0:本月  -1 上月  1下月
     * @return
     */
    public static int getMaxDate(int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, month);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getDaysInterval(String date) {
        Calendar fromCalendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        try {
            Date parse = sdf.parse(date);
            fromCalendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);
        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(new Date());
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);
        return (int) ((fromCalendar.getTimeInMillis() - toCalendar.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    public static long getFormatDayZero(String date) {
        if (TextUtils.isEmpty(date)) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        try {
            Date parse = sdf.parse(date);
            calendar.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    public static String getDateAndWeekInfo(long millis) {
        String week = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;

        }
        return formatDate(millis, FORMAT_TIME_DAY_NO_SECOND) + " " + week;
    }

    public static String getWeekInfo() {
        String week = "";
        Calendar calendar = Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK);
        switch (i) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;

        }
        return week;
    }


}