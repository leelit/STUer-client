package com.leelit.stuer.module_treehole.presenter;

import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.bean.TreeholeLocalInfo;
import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.module_treehole.model.TreeholeModel;
import com.leelit.stuer.module_treehole.viewinterface.ITreeholeView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholePresenter implements IPresenter {

    private ITreeholeView mView;
    private TreeholeModel mModel = new TreeholeModel();
    private Subscriber<List<TreeholeInfo>> mSubscriber1;
    private Subscriber<List<TreeholeLocalInfo>> mSubscriber2;

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
                if (treeholeInfos.isEmpty()) {
                    mView.showNoDataFromNet();
                    return;
                }
                List<TreeholeInfo> currentList = mView.getCurrentList();
                List<TreeholeInfo> copyList = new ArrayList<>(currentList);
                Collections.reverse(copyList); // 加上新的数据并处理顺序
                copyList.addAll(treeholeInfos);
                Collections.reverse(copyList);
                mView.showDataFromNet(copyList); // 展示
            }
        };
        mModel.getNewerData(mSubscriber1);
    }


    public void doLoadDataFromDb() {
        mView.showLoadingDbProgressDialog();
        mSubscriber2 = new Subscriber<List<TreeholeLocalInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDbProgressDialog();
            }

            @Override
            public void onNext(List<TreeholeLocalInfo> treeholeLocalInfos) {
                mView.dismissLoadingDbProgressDialog();
                if (treeholeLocalInfos.isEmpty()) {
                    mView.showNoDataInDb();
                    return;
                }
                Collections.reverse(treeholeLocalInfos);  // 原本时间顺序后 1 2 3 4 ， loadFromDb后展示为 4 3 2 1
                mView.showDataFromDb(treeholeLocalInfos);
            }
        };
        mModel.loadFromDb(mSubscriber2);
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
