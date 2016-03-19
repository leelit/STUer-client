package com.leelit.stuer.viewinterface;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface IBaseInfoPostView {
    void showPostProgressDialog();

    void dismissPostProgressDialog();

    void netError();

    void doAfterPostSuccessfully();
}
