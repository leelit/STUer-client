package com.leelit.stuer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.leelit.stuer.MyApplication;

/**
 * Created by Leelit on 2016/1/7.
 */
public class SPUtils {

    private static SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences(AppInfoUtils.getAppName(), Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sharedPreferences.edit();

    public static void save(String[] keys, String[] values) {
        for (int i = 0; i < keys.length; i++) {
            editor.putString(keys[i], values[i]);
        }
        editor.commit();
    }

    public static String get(String key) {
        return sharedPreferences.getString(key, "");
    }
}
