package com.leelit.stuer;

import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.AppInfoUtils;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/31.
 */
public class UpdatePresenter implements IPresenter {

    private IUpdateView mView;
    private UpdateModel mModel = new UpdateModel();
    private Subscriber<ResponseBody> mSubscriber1;

    public UpdatePresenter(IUpdateView view) {
        mView = view;
    }

    public void checkNewVersion() {
        mView.showCheckUpdateProgressDialog();
        mSubscriber1 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissCheckUpdateProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissCheckUpdateProgressDialog();
                String latestVersionAndInfo = "";
                String latestVersion = MyApplication.VERSION;
                String info = "";
                try {
                    latestVersionAndInfo = responseBody.string(); // 得到最新的版本
                    // ..-version-info
                    latestVersion = latestVersionAndInfo.split("-")[1];
                    info = latestVersionAndInfo.split("-")[2];
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (latestVersion.equals(MyApplication.VERSION)) {
                    mView.noNewVersion();
                } else {
                    String newVersionUrl = NetConstant.HOST + AppInfoUtils.getAppName() + "_" + latestVersion + ".apk";
                    mView.doAfterNewVersionExist(newVersionUrl, info);
                }
            }
        };
        mModel.getNewVersion(mSubscriber1);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        mView = null;
    }
}
