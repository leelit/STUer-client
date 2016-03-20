package com.leelit.stuer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.leelit.stuer.MyApplication;

/**
 * Created by Leelit on 2016/3/20.
 */
public class SettingUtils {

    public static final String SETTING_SP_NAME = "setting";

    private static SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences(SETTING_SP_NAME, Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sharedPreferences.edit();

    public static final String NO_OFFLINE_SELL = "no_offline_sell";

    public static boolean noOfflineSell() {
        return sharedPreferences.getBoolean(NO_OFFLINE_SELL, false);
    }

    ;
}
