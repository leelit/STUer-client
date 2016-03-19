package com.leelit.stuer.base_fragments.viewinterface;

import com.leelit.stuer.bean.BaseInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface IMyBaseInfoView {
    void stopRefreshing();

    void netError();

    void showData(List<List<? extends BaseInfo>> lists);

    void noData();

    void showDeleteProgressDialog(String message);

    void dismissDeleteProgressDialog();

    void deleteOrder(int position);


}
