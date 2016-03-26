package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.bean.TreeholeInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/24.
 */
public interface ITreeholeView {
    void stopRefreshing();

    void netError();

    void showDataFromDb(List<TreeholeComment> infos);

    void showLoadingDbProgressDialog();

    void dismissLoadingDbProgressDialog();

    void showNoDataInDb();

    void showDataFromNet(List<TreeholeInfo> infos);

    void showNoDataFromNet();

    List<TreeholeInfo> getCurrentList();
}
