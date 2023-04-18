package com.umpaytest.util;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/28 14:51
 */
public class TimeUtil {
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MONTH_PATTERN = "yyyy-MM";
    public static final String DATE_YEAR_PATTERN = "yyyy";
    public static final String DATE_YEARMONTH_PATTERN = "yyyyMM";
    public static final String DATE_YEARMONTHDAY_PATTERN = "yyyyMMdd";
    public static final String DATE_TIME_HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String HOUR_PATTERN = "HH";
    private static Pattern NUMBER_PATTERN = Pattern.compile("([0-9]{4})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])");
    private static Pattern NUMBER_PATTERN2 = Pattern.compile("(\\d{4})-(\\d{2})-(\\d{2})");

    public static void main(String[] args) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(format.parse("2022-12-01"));
        end.setTime(format.parse("2023-02-28"));
        List<String> interval = getInterval(start, end, Calendar.MONTH);
        System.out.println(interval);
    }

    public static List<String> getInterval(Calendar start, Calendar end, int field) {
        List<String> interval = new ArrayList<>();
        while (start.before(end)) {
            String time = getTime(start, field);
            if (!interval.contains(time)) {
                interval.add(time);
            }
            start.add(field, 1);
        }
        interval.add(getTime(end, field));
        return interval;
    }

    public static String getTime(Calendar calendar, int field) {
        DateFormat format = null;
        switch (field) {
            case Calendar.YEAR:
                format = new SimpleDateFormat("yyyy");
                break;
            case Calendar.MONTH:
                format = new SimpleDateFormat("yyyy-MM");
                break;
            case Calendar.DATE:
                format = new SimpleDateFormat("yyyy-MM-dd");
                break;
            default:
                break;
        }
        return format.format(calendar.getTime());
    }

    public static Date addDateHours(Date date, int hours) {
        DateTime dateTime = new DateTime(date);
        return dateTime.plusHours(hours).toDate();
    }

    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } else {
            return null;
        }
    }
}
