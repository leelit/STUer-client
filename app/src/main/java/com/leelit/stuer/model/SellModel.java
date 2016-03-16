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
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leelit on 2016/3/15.
 */
public class SellModel {
    private static final String BASE_URL = "http://192.168.191.1:8080/STUer/sell/";

    public static final MediaType IMAGE
            = MediaType.parse("image/*");


    public void query(String newerDateTime, Subscriber<List<SellInfo>> subscriber) {
        createService().query(newerDateTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addRecord(SellInfo sellInfo, Subscriber<ResponseBody> subscriber) {
        createService().addRecord(sellInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addRecordWithPhoto(File file, SellInfo sellInfo, Subscriber<ResponseBody> subscriber) {
        createService().addRecordWithPhoto(RequestBody.create(IMAGE, file), sellInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private SellService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(SellService.class);
    }

}
