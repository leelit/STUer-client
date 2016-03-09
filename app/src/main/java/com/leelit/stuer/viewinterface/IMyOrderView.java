package com.leelit.stuer.viewinterface;

import com.leelit.stuer.bean.BaseInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface IMyOrderView {
    void notRefreshing();

    void showInfos(List<List<? extends BaseInfo>> lists);

    void noInfos();

    void netError();

    void showDeleteProgressDialog(String message);

    void dismissDeleteProgressDialog();

    void deleteOrder(int position);
}
