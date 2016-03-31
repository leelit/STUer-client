package com.leelit.stuer.module_treehole.viewinterface;

import com.leelit.stuer.bean.TreeholeLocalInfo;
import com.leelit.stuer.bean.TreeholeInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/24.
 */
public interface ITreeholeView {
    void stopRefreshing();

    void netError();

    void showDataFromDb(List<TreeholeLocalInfo> infos);

    void showLoadingDbProgressDialog();

    void dismissLoadingDbProgressDialog();

    void showNoDataInDb();

    void showDataFromNet(List<TreeholeInfo> infos);

    void showNoDataFromNet();

    List<TreeholeInfo> getCurrentList();
}
