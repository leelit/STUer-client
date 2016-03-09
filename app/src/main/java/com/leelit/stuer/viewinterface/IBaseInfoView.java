package com.leelit.stuer.viewinterface;

import com.leelit.stuer.bean.BaseInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/8.
 */
public interface IBaseInfoView {

    void notRefreshing();

    void showInfos(List<? extends BaseInfo> list);

    void noInfos();

    void netError();

    void showPostProgressDialog();

    void dismissPostProgressDialog();

    void infoExist();

    void afterPostInfo();
}
