package com.leelit.stuer.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;

/**
 * Created by Leelit on 2016/3/16.
 */
public class ProgressDialogUtils {

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
        }
        if (!TextUtils.isEmpty(text)) {
            progressDialog.setMessage(text);
        }
        progressDialog.show();
    }

    public static void dismissProgressDialog() {
        progressDialog.dismiss();
        progressDialog = null;
    }

}
