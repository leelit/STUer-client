package com.leelit.stuer.module_treehole;

import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.bean.TreeholeInfo;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholePresenter implements IPresenter{

    private ITreeholeView mView;
    private TreeholeModel mModel = new TreeholeModel();
    private Subscriber<List<TreeholeInfo>> mSubscriber1;

    public TreeholePresenter(ITreeholeView view) {
        mView = view;
    }

    public void doLoadDataFromNet() {
        mSubscriber1 = new Subscriber<List<TreeholeInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.stopRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<TreeholeInfo> treeholeInfos) {
                mView.stopRefreshing();
                mView.showDataFromNet(treeholeInfos);
                if (treeholeInfos.isEmpty()) {
                    mView.showNoDataFromNet();
                }
            }
        };
        mModel.getNewerData(mSubscriber1);
    }

    @Override
    public void doClear() {

    }
}
