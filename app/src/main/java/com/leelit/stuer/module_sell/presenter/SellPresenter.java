package com.leelit.stuer.module_sell.presenter;

import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.dao.SellDao;
import com.leelit.stuer.module_sell.model.SellModel;
import com.leelit.stuer.module_sell.viewinterface.ISellView;
import com.leelit.stuer.utils.SettingUtils;

import java.util.ArrayList;
import java.util.Collections;
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
                if (mView != null) {
                    mView.stopRefreshing();
                    mView.netError();
                }
            }

            @Override
            public void onNext(List<SellInfo> sellInfos) {
                mView.stopRefreshing();
                if (sellInfos.isEmpty()) {
                    mView.showNoDataFromNet();
                    return;
                }
                List<SellInfo> currentList = mView.getCurrentList();
                List<SellInfo> copyList = new ArrayList<>(currentList);
                Collections.reverse(copyList); // 加上新的数据并处理顺序
                copyList.addAll(sellInfos);
                Collections.reverse(copyList);
                mView.showDataFromNet(copyList); // 展示
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
                mView.dismissLoadingDbProgressDialog();
                if (sellInfos.isEmpty()) {
                    mView.showNoDataInDb();
                    return;
                }
                Collections.reverse(sellInfos);  // 原本时间顺序后 1 2 3 4 ， loadFromDb后展示为 4 3 2 1
                mView.showDataFromDb(sellInfos);
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

    public void doIfNoShowOfflineSell() {
        if (!SettingUtils.noOfflineSell()) {
            return;  // 如果不展示才需要后续处理，如果双重否定即要展示时则不处理数据
        }
        List<SellInfo> currentList = mView.getCurrentList(); // not copy the same list
        List<SellInfo> offInfos = new ArrayList<>();
        for (SellInfo info : currentList) {
            if (info.getStatus().equals("off")) {
                offInfos.add(info);
            }
        }
        for (SellInfo info : offInfos) {
            currentList.remove(info);
        }
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
