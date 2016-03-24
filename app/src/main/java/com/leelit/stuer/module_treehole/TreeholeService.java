package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeInfo;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by Leelit on 2016/3/24.
 */
public interface TreeholeService {

    @POST("create")
    Observable<ResponseBody> addRecord(@Body TreeholeInfo info);

    @Multipart
    @POST("create/photo")
    Observable<ResponseBody> addRecordWithPhoto(@Part("photo") RequestBody photo, @Part("info") TreeholeInfo info);
}
