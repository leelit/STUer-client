package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.model.SellModel;
import com.leelit.stuer.presenter.IPresenter;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellPresenter implements IPresenter {
    private SellModel mModel = new SellModel();

    private ISellView mView;
    private Subscriber<List<SellInfo>> mSubscriber1;

    public SellPresenter(ISellView view) {
        mView = view;
    }


    public void doQueryList() {
        mSubscriber1 = new Subscriber<List<SellInfo>>() {
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
                mView.showInfos(sellInfos);
            }
        };
        mModel.query("", mSubscriber1);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        mView = null;
    }
}
