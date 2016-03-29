package com.leelit.stuer.module_treehole;

import com.leelit.stuer.bean.TreeholeComment;

import java.util.List;

/**
 * Created by Leelit on 2016/3/27.
 */
public interface ICommentView {
    void sendingCommentProgressDialog();

    void dismissProgressDialog();

    void netError();

    void loadingCommentProgressDialog();

    void refreshLikeAndUnlike(TreeholeComment comment);

    void succeededInSending();

    void noComment();

    void showComments(List<TreeholeComment.Comment> comment);
}
