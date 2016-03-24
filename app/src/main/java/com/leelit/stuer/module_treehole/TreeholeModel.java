package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.utils.SupportModelUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholeModel {

    private static final String BASE_URL = SupportModelUtils.HOST + "treehole/";


    private TreeholeService mService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(TreeholeService.class);


    public static final MediaType IMAGE
            = MediaType.parse("image/*");

    public void addRecord(TreeholeInfo info, Subscriber<ResponseBody> subscriber) {
        SupportModelUtils.toSubscribe(mService.addRecord(info), subscriber);

    }

    public void addRecordWithPhoto(File file, TreeholeInfo info, Subscriber<ResponseBody> subscriber) {
        SupportModelUtils.toSubscribe(mService.addRecordWithPhoto(RequestBody.create(IMAGE, file), info), subscriber);
    }

}
