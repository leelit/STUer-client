package com.leelit.stuer.sell;

import android.content.Context;

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

    Context getContext();

    void showGoodsOffLine(int position);

    void showContactDialog(String tel, String shortel, String wechat);
}
