package com.leelit.stuer.module_sell.viewinterface;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface IPicPostView {
    void showPostProgressDialog();

    void dismissPostProgressDialog();

    void netError();

    void doAfterPostSuccessfully();
}
