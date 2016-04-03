package com.leelit.stuer.utils;

import com.leelit.stuer.constant.NetConstant;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Leelit on 2016/3/17.
 */
public class SupportModelUtils {

    public static final String HOST = NetConstant.HOST;


    /**
     * 默认线程处理方式
     *
     * @param observable
     * @param subscriber
     */
    public static void toSubscribe(Observable observable, Subscriber subscriber) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
