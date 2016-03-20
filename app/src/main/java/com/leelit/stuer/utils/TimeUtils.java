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

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = df.format(new Date());
        return time;
    }

    public static Map<String, Integer> getDetailTime(String datetime) {
        Map<String, Integer> map = new HashMap<>();
        map.put("year", Integer.parseInt(datetime.substring(0, 4)));
        map.put("month", Integer.parseInt(datetime.substring(5, 7)));
        map.put("day", Integer.parseInt(datetime.substring(8, 10)));
        map.put("hour", Integer.parseInt(datetime.substring(11, 13)));
        map.put("minute", Integer.parseInt(datetime.substring(14, 16)));
        map.put("second", Integer.parseInt(datetime.substring(17, 19)));
        return map;
    }

    public static String compareNowWithBefore(String datetime) {
        String currentTime = TimeUtils.getCurrentTime();
        Map<String, Integer> nowMap = TimeUtils.getDetailTime(currentTime);
        Map<String, Integer> thenMap = TimeUtils.getDetailTime(datetime);
        if (nowMap.get("year") > thenMap.get("year") || nowMap.get("month") > thenMap.get("month")) {
            return datetime;
        }
        int dayCount = nowMap.get("day") - thenMap.get("day");
        if (dayCount > 2) {
            return datetime;
        } else if (dayCount == 2) {
            return "前天 " + thenMap.get("hour") + ":" + thenMap.get("minute");
        } else if (dayCount == 1) {
            return "昨天" + thenMap.get("hour") + ":" + thenMap.get("minute");
        }
        int hourCount = nowMap.get("hour") - thenMap.get("hour");
        if (hourCount != 0) {
            return hourCount + "小时前";
        }
        int minuteCount = nowMap.get("minute") - thenMap.get("minute");
        if (minuteCount != 0) {
            return minuteCount + "分钟前";
        }
        return "刚刚";
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
