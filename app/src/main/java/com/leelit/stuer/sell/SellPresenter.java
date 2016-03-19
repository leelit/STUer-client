package com.leelit.stuer.sell;

import android.util.Log;

import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.dao.SellDao;
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
                mView.showFormRefreshInfos(sellInfos);
                if (sellInfos.isEmpty()) {
                    mView.showNoInfosPleaseWait();
                } else {
                    mModel.save(sellInfos);
                }
            }
        };
        mModel.query(mSubscriber1);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        mView = null;
    }

    public void doLoadFromDb() {
        mView.showLoading();
        mModel.loadFromDb(new Subscriber<List<SellInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                // do nothing
                mView.dismissLoading();
            }

            @Override
            public void onNext(List<SellInfo> sellInfos) {
                mView.showFromLoadDbInfos(sellInfos);
                mView.dismissLoading();
                if (sellInfos.isEmpty()) {
                    mView.showNoInfosPleaseRefresh();
                }
            }
        });
    }

    public void doContactSeller(final SellInfo info, final int position) {
        mView.showLoading();
        mModel.checkGoodsStillHere(info.getUniquecode(), new Subscriber<SellInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoading();
                // 网络出错则不关心是否已售出，直接让同学联系商家
                mView.showContactDialog(info.getTel(), info.getShortTel(), info.getWechat());
                Log.e("tag", "hehe");
            }

            @Override
            public void onNext(SellInfo sellinfo) {
                mView.dismissLoading();
                if (sellinfo.getStatus().equals("off")) {
                    new SellDao().updateStatusInSell(sellinfo); // 这里同步操作，因为确保刷新时数据已变...
                    mView.showGoodsOffLine(position);
                } else {
                    mView.showContactDialog(info.getTel(), info.getShortTel(), info.getWechat());
                }
            }
        });
    }
}
