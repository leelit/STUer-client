package com.leelit.stuer.service;


import com.leelit.stuer.bean.DatingInfo;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface DateService {
    @GET("query")
    Observable<List<DatingInfo>> getGroupRecords(@Query("type") String type);

    @GET("query")
    Observable<List<List<DatingInfo>>> getPersonalRelativeRecords(@Query("imei") String imei);

    @POST("create")
    Observable<ResponseBody> addRecord(@Body DatingInfo datingInfo);

    @GET("delete")
    Observable<ResponseBody> finishOrder(@Query("uniquecode") String uniquecode);

    @GET("delete")
    Observable<ResponseBody> quitOrder(@QueryMap Map<String, String> map);
}
