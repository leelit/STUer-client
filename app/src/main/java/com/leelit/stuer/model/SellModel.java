package com.leelit.stuer.model;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.model.service.SellService;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/15.
 */
public class SellModel {
    private static final String BASE_URL = SupportUtils.HOST + "sell/";

    private SellService mService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(SellService.class);

    public static final MediaType IMAGE
            = MediaType.parse("image/*");


    public void query(String newerDateTime, Subscriber<List<SellInfo>> subscriber) {
        SupportUtils.toSubscribe(mService.query(newerDateTime), subscriber);
    }

    public void addRecord(SellInfo sellInfo, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.addRecord(sellInfo), subscriber);

    }

    public void addRecordWithPhoto(File file, SellInfo sellInfo, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.addRecordWithPhoto(RequestBody.create(IMAGE, file), sellInfo), subscriber);
    }


}
