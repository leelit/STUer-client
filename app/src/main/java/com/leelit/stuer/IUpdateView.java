package com.leelit.stuer;

/**
 * Created by Leelit on 2016/3/31.
 */
public interface IUpdateView {
    void showCheckUpdateProgressDialog();

    void dismissCheckUpdateProgressDialog();

    void netError();

    void doAfterNewVersionExist(String newVersionUrl);

    void noNewVersion();
}
