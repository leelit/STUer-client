package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface ISellView {
    void netError();

    void showFromLoadDbInfos(List<SellInfo> sellInfos);

    void notRefreshing();

    void showLoading();

    void dismissLoading();

    void showFormRefreshInfos(List<SellInfo> sellInfos);

    void showNoInfosPleaseRefresh();

    void showNoInfosPleaseWait();
}
