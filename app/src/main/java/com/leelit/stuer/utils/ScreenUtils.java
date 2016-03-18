package com.leelit.stuer.utils;

import com.leelit.stuer.MyApplication;

/**
 * Created by Leelit on 2016/3/17.
 */
public class ScreenUtils {
    public static int dp2px(float dp) {
        float scale = MyApplication.context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
