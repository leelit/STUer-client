package com.leelit.stuer.utils;

import com.leelit.stuer.MyApplication;
import com.leelit.stuer.R;

import java.util.Date;

/**
 * Created by Leelit on 2016/1/7.
 */
public class AppUtils {
    public static String getAppName() {
        return MyApplication.context.getString(R.string.app_name);
    }

    public static String getUniqueCode() {
        String imei = PhoneInfoUtils.getImei();
        Date date = new Date();
        String unique = imei + date.hashCode();
        return String.valueOf(unique.hashCode());
    }
}
