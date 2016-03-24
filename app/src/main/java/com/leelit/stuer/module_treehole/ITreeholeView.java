package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/24.
 */
public interface ITreeholeView {
    void stopRefreshing();

    void netError();

    void showDataFromNet(List<TreeholeInfo> treeholeInfos);

    void showNoDataFromNet();
}
