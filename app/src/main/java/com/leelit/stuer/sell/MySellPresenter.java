package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.model.SellModel;
import com.leelit.stuer.presenter.IPresenter;

import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/18.
 */
public class MySellPresenter implements IPresenter {

    private IMySellView mView;
    private SellModel mModel = new SellModel();

    public MySellPresenter(IMySellView view) {
        mView = view;
    }


    public void doLoadingInfos(String imei) {
        mModel.getPersonalSell(imei, new Subscriber<List<SellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.notRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<SellInfo> sellInfos) {
                mView.notRefreshing();
                Collections.reverse(sellInfos);  // 时间顺序变换为4 3 2 1
                mView.showInfos(sellInfos);
                if (sellInfos.isEmpty()) {
                    mView.noInfos();
                }
            }
        });
    }


    public void doOfflineTheOrder(String uniquecode, final int position) {
        mView.showLoading();
        mModel.offlineOrder(uniquecode, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoading();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissLoading();
                mView.offlineOrder(position);
            }
        });
    }

    @Override
    public void doClear() {

    }
}
