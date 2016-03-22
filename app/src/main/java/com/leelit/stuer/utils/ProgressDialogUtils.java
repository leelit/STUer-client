package com.leelit.stuer.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by Leelit on 2016/3/16.
 */
public class ProgressDialogUtils {

    private static ProgressDialog progressDialog;

    public static void show(Context context) {
        show(context, "");
    }

    public static void show(Context context, String text) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false); // 只能同时操作一个ProgressDialog
        if (!TextUtils.isEmpty(text)) {
            progressDialog.setMessage(text);
        }
        progressDialog.show();
    }

    public static void dismiss() {
        progressDialog.dismiss();
        progressDialog = null;
    }

}
