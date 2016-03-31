package com.leelit.stuer.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.leelit.stuer.MainActivity;
import com.leelit.stuer.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Leelit on 2016/3/29.
 */
public class UiUtils {

    @TargetApi(19)
    public static void setTranslucentStatusBar(Activity activity, NavigationView navigationView) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT && navigationView != null) {
            // fix 天坑 4.4 + drawerlayout + navigationView
            tianKeng(activity, activity.getResources().getColor(R.color.primary), navigationView);
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
        setTranslucentStatusBar(activity, null);
    }

    /**
     * snippet from https://github.com/niorgai/StatusBarCompat
     * <p/>
     * 直接使用会引起NavigationView上面空白一部分，需要将其margin - statusBarHeight
     */
    @TargetApi(19)
    private static void tianKeng(Activity activity, int statusColor, NavigationView navigationView) {
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        int statusBarHeight = getStatusBarHeight(activity);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);

        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            //if margin top has already set, just skip.
            if (lp != null && lp.topMargin < statusBarHeight && lp.height != statusBarHeight) {
                //do not use fitsSystemWindows
                ViewCompat.setFitsSystemWindows(mChildView, false);
                //add margin to content
                lp.topMargin += statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }

        View statusBarView = mContentView.getChildAt(0);
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height == statusBarHeight) {
            //if fake status bar view exist, we can setBackgroundColor and return.
            statusBarView.setBackgroundColor(statusColor);
            return;
        }
        statusBarView = new View(activity);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight);
        statusBarView.setBackgroundColor(statusColor);
        mContentView.addView(statusBarView, 0, lp);

        DrawerLayout.LayoutParams nvLp = (DrawerLayout.LayoutParams) navigationView.getLayoutParams();
        nvLp.topMargin -= statusBarHeight;
        navigationView.setLayoutParams(nvLp);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
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

    public static boolean isNightMode(AppCompatActivity activity) {
        int uiMode = activity.getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (MainActivity.mNightMode && dayNightUiMode != Configuration.UI_MODE_NIGHT_YES) {
            activity.getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            activity.recreate();
            return true;
        }
        return false;
    }
}
