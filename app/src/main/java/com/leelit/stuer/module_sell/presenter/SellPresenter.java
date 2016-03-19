package com.leelit.stuer.module_sell.presenter;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.dao.SellDao;
import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.module_sell.viewinterface.ISellView;
import com.leelit.stuer.module_sell.model.SellModel;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellPresenter implements IPresenter {
    private SellModel mModel = new SellModel();

    private ISellView mView;
    private Subscriber<List<SellInfo>> mSubscriber1;
    private Subscriber<List<SellInfo>> mSubscriber2;
    private Subscriber<SellInfo> mSubscriber3;

    public SellPresenter(ISellView view) {
        mView = view;
    }


    public void doLoadDataFromNet() {
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
                mView.showDataFromNet(sellInfos);
                if (sellInfos.isEmpty()) {
                    mView.showNoDataFromNet();
                } else {
                    mModel.save(sellInfos);
                }
            }
        };
        mModel.getNewerData(mSubscriber1);
    }


    public void doLoadDataFromDb() {
        mView.showLoadingDbProgressDialog();
        mSubscriber2 = new Subscriber<List<SellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                // do nothing
                mView.dismissLoadingDbProgressDialog();
            }

            @Override
            public void onNext(List<SellInfo> sellInfos) {
                mView.showDataFromDb(sellInfos);
                mView.dismissLoadingDbProgressDialog();
                if (sellInfos.isEmpty()) {
                    mView.showNoDataInDb();
                }
            }
        };
        mModel.loadFromDb(mSubscriber2);
    }

    public void doContactSeller(final SellInfo info, final int position) {
        mView.showLoadingDbProgressDialog();
        mSubscriber3 = new Subscriber<SellInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDbProgressDialog();
                // 网络出错则不关心是否已售出，直接让同学联系商家
                mView.showContactDialog(info.getTel(), info.getShortTel(), info.getWechat());
            }

            @Override
            public void onNext(SellInfo sellinfo) {
                mView.dismissLoadingDbProgressDialog();
                if (sellinfo.getStatus().equals("off")) {
                    new SellDao().updateStatusInSell(sellinfo); // 这里同步操作，因为确保刷新时数据已变...
                    mView.showGoodsOffline(position);
                } else {
                    mView.showContactDialog(info.getTel(), info.getShortTel(), info.getWechat());
                }
            }
        };
        mModel.checkGoodsStillHere(info.getUniquecode(), mSubscriber3);
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
        mView = null;
    }
}
