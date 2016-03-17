package com.leelit.stuer.presenter;

import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.model.CarpoolModel;
import com.leelit.stuer.model.DateModel;
import com.leelit.stuer.viewinterface.IPostView;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/9.
 */
public class PostInfoPresenter implements IPresenter {

    private IPostView mPostView;
    private Subscriber<ResponseBody> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;

    public PostInfoPresenter(IPostView postView) {
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
                mPostView.afterPost();
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
                mPostView.afterPost();
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
