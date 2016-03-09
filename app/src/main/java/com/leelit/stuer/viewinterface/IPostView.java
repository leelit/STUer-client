package com.leelit.stuer.viewinterface;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface IPostView {
    void showPostProgressDialog();

    void dismissPostProgressDialog();

    void netError();

    void afterPost();
}
