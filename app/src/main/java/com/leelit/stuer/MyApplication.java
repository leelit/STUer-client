package com.leelit.stuer;

import android.app.Application;
import android.content.Context;

/**
 * Created by Leelit on 2016/1/6.
 */
public class MyApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
