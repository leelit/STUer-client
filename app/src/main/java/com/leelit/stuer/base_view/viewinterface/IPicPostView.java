package com.leelit.stuer.base_view.viewinterface;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface IPicPostView {
    void showPostProgressDialog();

    void dismissPostProgressDialog();

    void netError();

    void doAfterPostSuccessfully();
}
