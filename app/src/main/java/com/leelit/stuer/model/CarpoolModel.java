package com.leelit.stuer.model;

import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.model.service.CarpoolService;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leelit on 2016/3/8.
 */
public class CarpoolModel {

    // model此处无法抽象，因为接口不同；
    // Retrofit使用Gson进行字符串解析，并且RxJava#Observable<T>不能使用通配符，所以Gson从String-Object时必须指定确切类型，如果指定父类，则会丢失信息。
    // 使用Retrofit配合Gson不管是post还是get，解析的类型都是特定的，父类会丢失子类信息。

    private static final String BASE_URL = "http://192.168.191.1:8080/STUer/carpool/";

    /**
     * can't not be Subscriber<List<BaseInfo>> or Subscriber<List<? extends/super BaseInfo>>
     * @param subscriber
     */
    public void getGroupRecords(Subscriber<List<CarpoolingInfo>> subscriber) {
        createService().getGroupRecords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getPersonalRelativeRecords(String imei, Subscriber<List<List<CarpoolingInfo>>> subscriber) {
        createService().getPersonalRelativeRecords(imei)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * can't not be BaseInfo here
     * @param carpoolingInfo
     * @param subscriber
     */
    public void addRecord(CarpoolingInfo carpoolingInfo, Subscriber<ResponseBody> subscriber) {
        createService().addRecord(carpoolingInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void quitOrder(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        createService().quitOrder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void finishOrder(String uniquecode, Subscriber<ResponseBody> subscriber) {
        createService().finishOrder(uniquecode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private CarpoolService createService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(CarpoolService.class);
    }
}
