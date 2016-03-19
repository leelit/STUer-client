package com.leelit.stuer.utils;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by Leelit on 2016/3/18.
 */
public class ContactUtils {
    public static AlertDialog createContactDialog(final Context context, final String tel, final String shorttel, final String wechat) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final String[] items = {"拨打长号", "拨打短号", "发送短信", "复制微信号"};
        return builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                        context.startActivity(intent1);
                        break;
                    case 1:
                        if (shorttel.equals("无")) {
                            Toast.makeText(context, "对方无短号", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shorttel));
                            context.startActivity(intent2);
                        }
                        break;
                    case 2:
                        Intent intent3 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + tel));
                        context.startActivity(intent3);
                        break;
                    case 3:
                        ClipboardManager myClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData myClip = ClipData.newPlainText("text", wechat);
                        myClipboard.setPrimaryClip(myClip);
                        Toast.makeText(context, "已复制微信号：" + wechat, Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }
        }).create();
    }
}
