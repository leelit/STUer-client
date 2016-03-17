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

/**
 * Created by Leelit on 2016/3/8.
 */
public class CarpoolModel {

    // model此处无法抽象，因为接口不同；
    // Retrofit使用Gson进行字符串解析，并且RxJava#Observable<T>不能使用通配符，所以Gson从String-Object时必须指定确切类型，如果指定父类，则会丢失信息。
    // 使用Retrofit配合Gson不管是post还是get，解析的类型都是特定的，父类会丢失子类信息。

    private static final String BASE_URL = SupportUtils.HOST + "carpool/";


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();
    private CarpoolService mService = retrofit.create(CarpoolService.class);

    /**
     * can't not be Subscriber<List<BaseInfo>> or Subscriber<List<? extends/super BaseInfo>>
     *
     * @param subscriber
     */
    public void getGroupRecords(Subscriber<List<CarpoolingInfo>> subscriber) {
        SupportUtils.toSubscribe(mService.getGroupRecords(), subscriber);
    }

    public void getPersonalRelativeRecords(String imei, Subscriber<List<List<CarpoolingInfo>>> subscriber) {
        SupportUtils.toSubscribe(mService.getPersonalRelativeRecords(imei), subscriber);
    }

    /**
     * can't not be BaseInfo here
     *
     * @param carpoolingInfo
     * @param subscriber
     */
    public void addRecord(CarpoolingInfo carpoolingInfo, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.addRecord(carpoolingInfo), subscriber);

    }

    public void quitOrder(Map<String, String> map, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.quitOrder(map), subscriber);

    }

    public void finishOrder(String uniquecode, Subscriber<ResponseBody> subscriber) {
        SupportUtils.toSubscribe(mService.finishOrder(uniquecode), subscriber);
    }


}
