package com.leelit.stuer.model;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.dao.SellDao;
import com.leelit.stuer.model.service.SellService;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

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


    public void getNewerData(Subscriber<List<SellInfo>> subscriber) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SellDao sellDao = new SellDao();
                String str = sellDao.getLatestDatetime(); // 得到本地数据库中最新的时间
                subscriber.onNext(str);
            }
        }).flatMap(new Func1<String, Observable<List<SellInfo>>>() {

            @Override
            public Observable<List<SellInfo>> call(String s) {
                return mService.getNewerData(s);
            }
        });
        SupportUtils.toSubscribe(observable, subscriber);  // 想服务器请求比这个时间更新的数据
    }

    public void addRecord(SellInfo sellInfo, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.addRecord(sellInfo), subscriber);

    }

    public void addRecordWithPhoto(File file, SellInfo sellInfo, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.addRecordWithPhoto(RequestBody.create(IMAGE, file), sellInfo), subscriber);
    }


    public void loadFromDb(Subscriber<List<SellInfo>> subscriber) {
        Observable<List<SellInfo>> observable = Observable.create(new Observable.OnSubscribe<List<SellInfo>>() {
            @Override
            public void call(Subscriber<? super List<SellInfo>> subscriber) {
                SellDao dao = new SellDao();
                List<SellInfo> list = dao.getAll("sell");
                Collections.reverse(list);  // 原本时间顺序后 1 2 3 4 ， loadFromDb后展示为 4 3 2 1
                subscriber.onNext(list);
            }

        });
        SupportUtils.toSubscribe(observable, subscriber);
    }

    public void save(final List<SellInfo> sellInfos) {
        Executors.newCachedThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                new SellDao().save(sellInfos);
            }
        });
    }

    public void checkGoodsStillHere(String uniquecode, Subscriber<SellInfo> subscriber) {
        SupportUtils.toSubscribe(mService.checkFoodsStillHere(uniquecode), subscriber);
    }

    public void getPersonalSell(String imei, Subscriber<List<SellInfo>> subscriber) {
        SupportUtils.toSubscribe(mService.getPersonalRecords(imei), subscriber);
    }


    public void offlineOrder(String uniquecode, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.offlineOrder(uniquecode), subscriber);
    }
}
