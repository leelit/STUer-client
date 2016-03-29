package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeComment;

/**
 * Created by Leelit on 2016/3/27.
 */
public interface ICommentView {
    void sendingCommentProgressDialog();

    void dismissProgressDialog();

    void netError();

    void loadingCommentProgressDialog();

    void refreshView(TreeholeComment comment);

    void succeededInSending();
}
