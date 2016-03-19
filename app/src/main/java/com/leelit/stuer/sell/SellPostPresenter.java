package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.model.SellModel;
import com.leelit.stuer.presenter.IPresenter;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellPostPresenter implements IPresenter{
    private SellModel mModel = new SellModel();

    private ISellPostView mView;
    private Subscriber<ResponseBody> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;

    public SellPostPresenter(ISellPostView view) {
        mView = view;
    }

    public void doPost(SellInfo sellInfo) {
        mView.showPostProgressDialog();
        mSubscriber1 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissPostProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissPostProgressDialog();
                mView.doAfterPostSuccessfully();
            }
        };
        mModel.addRecord(sellInfo, mSubscriber1);
    }

    public void doPostWithPhoto(File file, SellInfo sellInfo) {
        mView.showPostProgressDialog();
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissPostProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissPostProgressDialog();
                mView.doAfterPostSuccessfully();
            }
        };
        mModel.addRecordWithPhoto(file, sellInfo, mSubscriber2);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        if (mSubscriber2 != null) {
            mSubscriber2.unsubscribe();
        }
        mView = null;
    }
}
