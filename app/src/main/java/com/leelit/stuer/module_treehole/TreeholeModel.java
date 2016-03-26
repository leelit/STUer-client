package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.dao.TreeholeDao;
import com.leelit.stuer.utils.SupportModelUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholeModel {

    private static final String BASE_URL = SupportModelUtils.HOST + "treehole/";


    private TreeholeService mService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build()
            .create(TreeholeService.class);


    public static final MediaType IMAGE
            = MediaType.parse("image/*");

    public void addRecord(TreeholeInfo info, Subscriber<ResponseBody> subscriber) {
        SupportModelUtils.toSubscribe(mService.addRecord(info), subscriber);

    }

    public void addRecordWithPhoto(File file, TreeholeInfo info, Subscriber<ResponseBody> subscriber) {
        SupportModelUtils.toSubscribe(mService.addRecordWithPhoto(RequestBody.create(IMAGE, file), info), subscriber);
    }

    public void getNewerData(final Subscriber<List<TreeholeInfo>> subscriber) {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(TreeholeDao.getLatestDatetime());
            }
        }).flatMap(new Func1<String, Observable<List<TreeholeInfo>>>() {
            @Override
            public Observable<List<TreeholeInfo>> call(String s) {
                return mService.getNewerData(s);
            }
        }).doOnNext(new Action1<List<TreeholeInfo>>() {
            @Override
            public void call(List<TreeholeInfo> treeholeInfos) {
                TreeholeDao.save(treeholeInfos);
            }
        });
        SupportModelUtils.toSubscribe(observable, subscriber);
    }

    public void loadFromDb(Subscriber<List<TreeholeComment>> subscriber) {
        Observable<List<TreeholeComment>> observable = Observable.create(new Observable.OnSubscribe<List<TreeholeComment>>() {
            @Override
            public void call(Subscriber<? super List<TreeholeComment>> subscriber) {
                List<TreeholeComment> list = TreeholeDao.getAll();
                subscriber.onNext(list);
            }

        });
        SupportModelUtils.toSubscribe(observable, subscriber);
    }
}
