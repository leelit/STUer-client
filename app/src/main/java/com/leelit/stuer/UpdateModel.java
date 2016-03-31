package com.leelit.stuer;

import com.leelit.stuer.utils.SupportModelUtils;

import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/31.
 */
public class UpdateModel {

    private static final String BASE_URL = SupportModelUtils.HOST;

    private UpdateService mService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(UpdateService.class);

    public void getNewVersion(Subscriber<ResponseBody> subscriber) {
        SupportModelUtils.toSubscribe(mService.getNewVersion(),subscriber);
    }


}
