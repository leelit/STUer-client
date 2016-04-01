package com.leelit.stuer.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leelit on 2016/3/17.
 */
public class TimeUtils {

    private static final int DAY_MS = 24 * 60 * 60 * 1000;
    private static final int HOUR_MS = (60 * 60 * 1000);
    ;
    private static final int MIN_MS = (60 * 1000);

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        return time;
    }

    public static Map<String, String> getDetailTime(String datetime) {
        Map<String, String> map = new HashMap<>();
        map.put("year", datetime.substring(0, 4));
        map.put("month", datetime.substring(5, 7));
        map.put("day", datetime.substring(8, 10));
        map.put("hour", datetime.substring(11, 13));
        map.put("minute", datetime.substring(14, 16));
        map.put("second", datetime.substring(17, 19));
        return map;
    }

    public static String compareNowWithBefore(String datetime) {
        String noSecondDatetime = new StringBuilder(datetime).delete(datetime.length() - 3, datetime.length()).toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Map<String, String> detail = getDetailTime(datetime);
            Date now = df.parse(getCurrentTime());
            Date date = df.parse(datetime);
            long timeGap = now.getTime() - date.getTime();
            long dayGap = timeGap / DAY_MS;
            if (dayGap >= 30) {
                return noSecondDatetime;
            } else if (dayGap > 2) {
                return new StringBuilder().append(detail.get("month")).append("-").append(detail.get("day")).append(" ").append(detail.get("hour")).append(":").append(detail.get("minute")).toString();
            } else if (dayGap == 2) {
                return "前天 " + detail.get("hour") + ":" + detail.get("minute");
            } else if (dayGap == 1) {
                return "昨天 " + detail.get("hour") + ":" + detail.get("minute");
            }
            long hourGap = (timeGap / HOUR_MS - dayGap * 24);
            if (hourGap > 0) {
                return hourGap + "小时前";
            }
            long minGap = ((timeGap / MIN_MS) - dayGap * 24 * 60 - hourGap * 60);
            if (minGap > 0) {
                return minGap + "分钟前";
            } else if (minGap == 0) {
                return "刚刚";
            } else {
                return noSecondDatetime;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return noSecondDatetime;
        }
    }

    public static Date stringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
