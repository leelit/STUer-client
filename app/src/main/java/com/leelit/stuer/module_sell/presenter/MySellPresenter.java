package com.leelit.stuer.module_sell.presenter;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.module_sell.viewinterface.IMySellView;
import com.leelit.stuer.module_sell.model.SellModel;

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
    private Subscriber<List<SellInfo>> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;

    public MySellPresenter(IMySellView view) {
        mView = view;
    }


    public void doLoadingData(String imei) {
        mSubscriber1 = new Subscriber<List<SellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.stopRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<SellInfo> sellInfos) {
                mView.stopRefreshing();
                Collections.reverse(sellInfos);  // 时间顺序变换为4 3 2 1
                mView.showData(sellInfos);
                if (sellInfos.isEmpty()) {
                    mView.noData();
                }
            }
        };
        mModel.getPersonalSell(imei, mSubscriber1);
    }


    public void doOfflineSell(String uniquecode, final int position) {
        mView.showOffSellProgressDialog();
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissOffSellProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissOffSellProgressDialog();
                mView.offlineSell(position);
            }
        };
        mModel.offlineOrder(uniquecode, mSubscriber2);
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
