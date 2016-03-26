package com.leelit.stuer.module_sell.viewinterface;

import com.leelit.stuer.bean.SellInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/18.
 */
public interface IMySellView {

    void stopRefreshing();

    void netError();

    void showData(List<SellInfo> sellInfos);

    void noData();

    void showOffSellProgressDialog();

    void dismissOffSellProgressDialog();

    void doAfterOfflineSell(int position);

    List<SellInfo> getCurrentList();
}
