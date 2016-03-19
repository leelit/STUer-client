package com.leelit.stuer.base_fragments.viewinterface;

import com.leelit.stuer.bean.BaseInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/8.
 */
public interface IBaseInfoView {

    void stopRefreshing();

    void netError();

    void showData(List<? extends BaseInfo> list);

    void noData();

    void showJoinProgressDialog();

    void dismissJoinProgressDialog();

    void showAlreadyJoin();

    void doAfterJoinSuccessfully();

}
