package com.leelit.stuer.base_presenter;

import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.module_baseinfo.carpool.model.CarpoolModel;
import com.leelit.stuer.module_baseinfo.date.model.DateModel;
import com.leelit.stuer.base_fragments.viewinterface.IBaseInfoPostView;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/9.
 */
public class BaseInfoPostPresenter implements IPresenter {

    private IBaseInfoPostView mPostView;
    private Subscriber<ResponseBody> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;

    public BaseInfoPostPresenter(IBaseInfoPostView postView) {
        mPostView = postView;
    }

    public void doCarpoolPost(final CarpoolingInfo info) {
        mPostView.showPostProgressDialog();
        mSubscriber1 = new Subscriber<ResponseBody>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                mPostView.dismissPostProgressDialog();
                mPostView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mPostView.dismissPostProgressDialog();
                mPostView.doAfterPostSuccessfully();
            }
        };
        new CarpoolModel().addRecord(info, mSubscriber1);
    }

    public void doDatePost(final DatingInfo info) {
        mPostView.showPostProgressDialog();
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPostView.dismissPostProgressDialog();
                mPostView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mPostView.dismissPostProgressDialog();
                mPostView.doAfterPostSuccessfully();
            }
        };
        new DateModel().addRecord(info, mSubscriber2);
    }


    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        if (mSubscriber2 != null) {
            mSubscriber2.unsubscribe();
        }
        mPostView = null;
    }
}
