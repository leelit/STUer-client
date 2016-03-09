package com.leelit.stuer.presenter;

import com.leelit.stuer.PostInfoActivity;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.model.CarpoolModel;
import com.leelit.stuer.model.DateModel;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.viewinterface.IPostView;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/9.
 */
public class PostInfoPresenter {

    private IPostView mPostView;

    public PostInfoPresenter(IPostView postView) {
        mPostView = postView;
    }

    public void doCarpoolPost(final CarpoolingInfo info) {
        mPostView.showPostProgressDialog();
        new CarpoolModel().addRecord(info, new Subscriber<ResponseBody>() {
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
                saveSP(info);
                mPostView.dismissPostProgressDialog();
                mPostView.afterPost();
            }
        });
    }

    public void doDatePost(final DatingInfo info) {
        mPostView.showPostProgressDialog();
        new DateModel().addRecord(info, new Subscriber<ResponseBody>() {
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
                saveSP(info);
                mPostView.dismissPostProgressDialog();
                mPostView.afterPost();
            }
        });
    }

    private void saveSP(BaseInfo host) {
        String[] values = {host.getName(), host.getTel(), host.getShortTel(), host.getWechat()};
        SPUtils.save(PostInfoActivity.keys, values);
    }
}
