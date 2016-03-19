package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/18.
 */
public interface IMySellView {
    void showLoading();

    void dismissLoading();

    void netError();

    void showInfos(List<SellInfo> sellInfos);

    void notRefreshing();

    void noInfos();

    void offlineOrder(int position);
}
