package com.leelit.stuer.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.leelit.stuer.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Leelit on 2016/3/29.
 */
public class UiUtils {

    @TargetApi(19)
    public static void setTranslucentStatusBar(Activity activity, boolean hasDrawerLayout) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && hasDrawerLayout) {
            // todo 天坑 4.4 + drawerlayout
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                SystemBarTintManager tintManager = new SystemBarTintManager(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(activity.getResources().getColor(R.color.primary));
            }
        }
    }

    @TargetApi(19)
    public static void setTranslucentStatusBar(Activity activity) {
        setTranslucentStatusBar(activity, false);
    }

    public static void initBaseToolBar(final AppCompatActivity activity, Toolbar mToolbar, String title) {
        activity.setSupportActionBar(mToolbar);
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

}
