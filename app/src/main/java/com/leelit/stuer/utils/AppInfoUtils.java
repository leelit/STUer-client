package com.leelit.stuer.utils;

import android.app.Activity;
import android.telephony.TelephonyManager;

import com.leelit.stuer.MyApplication;
import com.leelit.stuer.R;

import java.util.Date;

/**
 * Created by Leelit on 2016/1/6.
 */
public class AppInfoUtils {
    public static String getAppName() {
        return MyApplication.context.getString(R.string.app_name);
    }

    public static String getImei() {
        TelephonyManager tm = ((TelephonyManager) MyApplication.context.getSystemService(Activity.TELEPHONY_SERVICE));
        String imei = tm.getDeviceId();
        if (imei != null && !imei.isEmpty()) {
            return imei;
        }
        return "";
    }

    /**
     * 产生唯一订单号
     * @return
     */
    public static String getUniqueCode() {
        String imei = AppInfoUtils.getImei();
        Date date = new Date();
        String unique = imei + date.hashCode();
        return String.valueOf(unique.hashCode());
    }
}
