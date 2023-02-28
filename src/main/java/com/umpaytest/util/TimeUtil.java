package com.umpaytest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Hu.ChangLiang
 * @date 2023/2/28 14:51
 */
public class TimeUtil {
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
}
