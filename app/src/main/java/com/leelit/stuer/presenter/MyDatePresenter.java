package com.leelit.stuer.presenter;

import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.model.DateModel;
import com.leelit.stuer.viewinterface.IMyBaseInfoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/9.
 */
public class MyDatePresenter implements IMyBaseInfoPresenter,IPresenter {

    private DateModel mModel = new DateModel();

    private IMyBaseInfoView mView;
    private Subscriber<List<List<DatingInfo>>> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;
    private Subscriber<ResponseBody> mSubscriber3;

    public MyDatePresenter(IMyBaseInfoView view) {
        mView = view;
    }

    @Override
    public void doLoadingData(String imei) {
        mSubscriber1 = new Subscriber<List<List<DatingInfo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.stopRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<List<DatingInfo>> lists) {
                mView.stopRefreshing();
                List<List<? extends BaseInfo>> result = new ArrayList<>();
                for (int i = 0; i < lists.size(); i++) {
                    result.add(lists.get(i));
                }
                mView.showData(result);
                if (lists.isEmpty()) {
                    mView.noData();
                }
            }
        };
        mModel.getPersonalRelativeRecords(imei, mSubscriber1);
    }

    @Override
    public void doQuitOrder(Map<String, String> map, final int position) {
        mView.showDeleteProgressDialog("退出中...");
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDeleteProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissDeleteProgressDialog();
                mView.deleteOrder(position);
            }
        };
        mModel.quitOrder(map, mSubscriber2);
    }

    @Override
    public void doFinishOrder(String uniquecode, final int position) {
        mView.showDeleteProgressDialog("解散中...");
        mSubscriber3 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDeleteProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissDeleteProgressDialog();
                mView.deleteOrder(position);
            }
        };
        mModel.finishOrder(uniquecode, mSubscriber3);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        if (mSubscriber2 != null) {
            mSubscriber2.unsubscribe();
        }
        if (mSubscriber3 != null) {
            mSubscriber3.unsubscribe();
        }
        mView = null;
    }
}
