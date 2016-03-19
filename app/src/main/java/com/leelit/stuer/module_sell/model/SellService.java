package com.leelit.stuer.module_sell.model;

import com.leelit.stuer.bean.SellInfo;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface SellService {

    @POST("create")
    Observable<ResponseBody> addRecord(@Body SellInfo baseInfo);

    @Multipart
    @POST("create/photo")
    Observable<ResponseBody> addRecordWithPhoto(@Part("photo") RequestBody photo, @Part("info") SellInfo baseInfo);

    @GET("query")
    Observable<List<SellInfo>> getNewerData(@Query("newer") String newer);

    @GET("query")
    Observable<List<SellInfo>> getPersonalRecords(@Query("imei") String imei);

    @GET("check")
    Observable<SellInfo> checkFoodsStillHere(@Query("uniquecode") String uniquecode);

    @GET("delete")
    Observable<ResponseBody> offlineOrder(@Query("uniquecode") String uniquecode);
}