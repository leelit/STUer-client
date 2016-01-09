package com.leelit.stuer.utils;

import android.app.Activity;
import android.telephony.TelephonyManager;

import com.leelit.stuer.MyApplication;

/**
 * Created by Leelit on 2016/1/6.
 */
public class PhoneInfoUtils {
    public static String getImei() {
        TelephonyManager tm = ((TelephonyManager) MyApplication.context.getSystemService(Activity.TELEPHONY_SERVICE));
        String imei = tm.getDeviceId();
        if (imei != null && !imei.isEmpty()) {
            return imei;
        }
        return "";
    }
}
