package com.leelit.stuer.utils;

import android.util.Log;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by Leelit on 2016/3/17.
 */
public class TimeUtilsTest extends TestCase {

    public void testExpressTime() throws Exception {
        // current time 2016-03-17 23:00:05
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-03-17 23:00:00"));
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-03-17 12:00:00"));
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-03-16 23:00:00"));
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-03-15 23:00:00"));
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-03-14 23:00:00"));
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-03-13 23:00:00"));

        Log.e("tag", TimeUtils.compareNowWithBefore("2015-03-17 23:00:00"));
        Log.e("tag", TimeUtils.compareNowWithBefore("2016-02-17 23:00:00"));


    }

    public void testStringToDate() throws Exception {
        Date date1 = TimeUtils.stringToDate("2016/3/24 11:14", "yyyy/MM/dd HH:mm");
        Date date2 = TimeUtils.stringToDate(TimeUtils.getCurrentTime(), "yyyy-MM-dd HH:mm:ss");
        assertEquals(true, date1.after(date2));
        assertEquals(false,date1.before(date2));
    }
}