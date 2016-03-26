package com.leelit.stuer.module_sell.viewinterface;

import com.leelit.stuer.bean.SellInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface ISellView {

    void stopRefreshing();

    void netError();

    void showDataFromDb(List<SellInfo> sellInfos);

    void showLoadingDbProgressDialog();

    void dismissLoadingDbProgressDialog();

    void showNoDataInDb();

    void showDataFromNet(List<SellInfo> sellInfos);

    void showNoDataFromNet();

    List<SellInfo> getCurrentList();

    void showGoodsOffline(int position);

    void showContactDialog(String tel, String shortel, String wechat);
}
