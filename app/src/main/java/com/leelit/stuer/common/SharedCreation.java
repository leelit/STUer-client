package com.leelit.stuer.common;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Leelit on 2016/1/11.
 */
public class SharedCreation {


    public static ProgressDialog createDialog(Context context, String message) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        return dialog;
    }

    public static ProgressDialog createDialog(Context context, String message, int theme) {
        ProgressDialog dialog = new ProgressDialog(context, theme);
        dialog.setMessage(message);
        return dialog;
    }

}
