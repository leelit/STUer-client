package com.leelit.stuer.module_sell.viewinterface;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface ISellPostView {
    void showPostProgressDialog();

    void dismissPostProgressDialog();

    void netError();

    void doAfterPostSuccessfully();
}
