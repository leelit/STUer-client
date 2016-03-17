package com.leelit.stuer.model;


import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.model.service.DateService;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/8.
 */
public class DateModel {

    private static final String BASE_URL = SupportUtils.HOST + "date/";

    private DateService mService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(DateService.class);


    public void getGroupRecords(String type, Subscriber<List<DatingInfo>> subscriber) {
        SupportUtils.toSubscribe(mService.getGroupRecords(type), subscriber);

    }

    public void getPersonalRelativeRecords(String imei, Subscriber<List<List<DatingInfo>>> subscriber) {
        SupportUtils.toSubscribe(mService.getPersonalRelativeRecords(imei), subscriber);
    }

    public void addRecord(DatingInfo datingInfo, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.addRecord(datingInfo), subscriber);
    }

    public void quitOrder(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.quitOrder(map), subscriber);
    }

    public void finishOrder(String uniquecode, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.finishOrder(uniquecode), subscriber);
    }


}
