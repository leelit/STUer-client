package com.leelit.stuer.utils;

import android.util.Log;

import com.leelit.stuer.utils.xinge.XingeApp;

import org.json.JSONException;

import java.util.concurrent.Executors;

/**
 * Created by Leelit on 2016/4/23.
 */
public class XingeUtils {

    private static final long XINGE_ACCESS_ID = 2100195691;
    private static final String XINGE_SECRET_KEY = "feb3e40e0d6f24af0a3564fe56ec3f58";

    public static final String COMMON_Title = "STUer";
    public static final String CARPOOL_Message = "你有新的拼友";
    public static final String DATE_Message = "你有新的约友";


    public static void pushToAccount(String message, String account) {
        pushToAccount(COMMON_Title, message, account, null);
    }

    public static void pushToAccount(final String title, final String message, final String account, final String activity) {
        // 必须在子线程完成
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("tag", XingeApp.pushAccountAndroid(XINGE_ACCESS_ID, XINGE_SECRET_KEY, title, message, account, activity).toString());
                } catch (JSONException e) {
                    // 不关心推送失败
                    e.printStackTrace();
                }
            }
        });
    }

}
