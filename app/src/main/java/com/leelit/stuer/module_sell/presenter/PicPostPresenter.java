package com.leelit.stuer.module_sell.presenter;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.module_sell.viewinterface.IPicPostView;
import com.leelit.stuer.module_sell.model.SellModel;
import com.leelit.stuer.module_treehole.TreeholeModel;

import java.io.File;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/16.
 */
public class PicPostPresenter implements IPresenter {
    private SellModel mSellModel = new SellModel();
    private TreeholeModel mTreeholeModel = new TreeholeModel();

    private IPicPostView mView;
    private Subscriber<ResponseBody> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;
    private Subscriber<ResponseBody> mSubscriber3;
    private Subscriber<ResponseBody> mSubscriber4;

    public PicPostPresenter(IPicPostView view) {
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
                showError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                showSuccess();
            }
        };
        mSellModel.addRecord(sellInfo, mSubscriber1);
    }


    public void doPostWithPhoto(File file, SellInfo sellInfo) {
        mView.showPostProgressDialog();
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                showSuccess();
            }
        };
        mSellModel.addRecordWithPhoto(file, sellInfo, mSubscriber2);
    }


    public void doPost(TreeholeInfo treeholeInfo) {
        mView.showPostProgressDialog();
        mSubscriber3 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                showSuccess();
            }
        };
        mTreeholeModel.addRecord(treeholeInfo, mSubscriber3);

    }


    public void doPostWithPhoto(File file, TreeholeInfo treeholeInfo) {
        mView.showPostProgressDialog();
        mSubscriber4 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                showError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                showSuccess();
            }
        };
        mTreeholeModel.addRecordWithPhoto(file, treeholeInfo, mSubscriber4);
    }

    private void showError() {
        mView.dismissPostProgressDialog();
        mView.netError();
    }

    private void showSuccess() {
        mView.dismissPostProgressDialog();
        mView.doAfterPostSuccessfully();
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
        if (mSubscriber4 != null) {
            mSubscriber4.unsubscribe();
        }
        mView = null;
    }
}
