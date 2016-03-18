package com.leelit.stuer.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

/**
 * Created by Leelit on 2016/3/18.
 */
public class ImageUtils {


    /**
     * @param imageView
     * @return byte[]
     */
    public static byte[] image2byte(ImageView imageView) {
        if (imageView == null) {
            return null;
        }
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }
}