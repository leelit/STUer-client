package com.leelit.stuer;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Leelit on 2016/3/31.
 */
public interface UpdateService {

    @GET("version.txt")
    Observable<ResponseBody> getNewVersion();
}
