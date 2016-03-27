package com.leelit.stuer.module_treehole;

import com.leelit.stuer.utils.AppInfoUtils;

/**
 * Created by Leelit on 2016/3/27.
 */
public class CommentPresenter {

    private TreeholeModel mModel = new TreeholeModel();

    private ICommentView mView;

    public CommentPresenter(ICommentView view) {
        mView = view;
    }

    public void doLikeJob(String uniquecode, boolean isLike) {
        mModel.doLikeJob(uniquecode, AppInfoUtils.getImei(),isLike);
    }

    public void doUnlikeJob(String uniquecode, boolean isUnlike) {
        mModel.doUnlikeJob(uniquecode, AppInfoUtils.getImei(), isUnlike);
    }
}
