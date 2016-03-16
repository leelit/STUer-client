package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.model.SellModel;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellPostPresenter {
    private SellModel mModel = new SellModel();

    private ISellPostView mView;

    public SellPostPresenter(ISellPostView view) {
        mView = view;
    }

    public void doPost(SellInfo sellInfo) {
        mView.showProgressDialog();
        mModel.addRecord(sellInfo, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissProgressDialog();
                mView.postSuccessfully();
            }
        });
    }

    public void doPostWithPhoto(File file, SellInfo sellInfo) {
        mView.showProgressDialog();
        mModel.addRecordWithPhoto(file, sellInfo, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissProgressDialog();
                mView.postSuccessfully();
            }
        });
    }
}
