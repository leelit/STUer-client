package com.leelit.stuer.sell;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface ISellPostView {
    void showProgressDialog();

    void dismissProgressDialog();

    void netError();

    void postSuccessfully();
}
