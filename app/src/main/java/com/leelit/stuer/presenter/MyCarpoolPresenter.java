package com.leelit.stuer.presenter;

import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.model.CarpoolModel;
import com.leelit.stuer.viewinterface.IMyOrderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/9.
 */
public class MyCarpoolPresenter {

    private CarpoolModel mModel = new CarpoolModel();

    private IMyOrderView mView;

    public MyCarpoolPresenter(IMyOrderView view) {
        mView = view;
    }

    public void doLoadingInfos(String imei) {
        mModel.getPersonalRelativeRecords(imei, new Subscriber<List<List<CarpoolingInfo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.notRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<List<CarpoolingInfo>> lists) {
                mView.notRefreshing();
                List<List<? extends BaseInfo>> result = new ArrayList<>();
                for (int i = 0; i < lists.size(); i++) {
                    result.add(lists.get(i));
                }
                mView.showInfos(result);
                if (lists.isEmpty()) {
                    mView.noInfos();
                }
            }
        });
    }


    public void doQuitOrder(Map<String, String> map, final int position) {
        mView.showDeleteProgressDialog("退出中...");
        mModel.quitOrder(map, new Subscriber<ResponseBody>() {
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
        });
    }

    public void doFinishOrder(String uniquecode, final int position) {
        mView.showDeleteProgressDialog("解散中...");
        mModel.finishOrder(uniquecode, new Subscriber<ResponseBody>() {
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
        });
    }
}
