package com.leelit.stuer.fragments;

import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.CarpoolAdapter;
import com.leelit.stuer.bean.CarpoolingInfo;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Leelit on 2016/1/17.
 */
public class DatingFragment extends BaseListFragment {


    @Override
    int bindInflateRes() {
        return R.layout.fragment_base_list;
    }

    @Override
    BaseListAdapter bindAdapter() {
        return new CarpoolAdapter(new ArrayList<CarpoolingInfo>());
    }

    @Override
    void refreshTask() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext("stop");
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    void onItemClickEvent(View view, int position) {

    }
}
