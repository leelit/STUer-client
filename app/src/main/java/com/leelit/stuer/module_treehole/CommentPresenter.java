package com.leelit.stuer.module_treehole;

import android.util.Log;

import com.leelit.stuer.LoginActivity;
import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.SPUtils;

import okhttp3.ResponseBody;
import rx.Subscriber;

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
        mModel.doLikeJob(uniquecode, AppInfoUtils.getImei(), isLike);
    }

    public void doUnlikeJob(String uniquecode, boolean isUnlike) {
        mModel.doUnlikeJob(uniquecode, AppInfoUtils.getImei(), isUnlike);
    }

    public void doSendComment(String uniquecode, String commentText) {
        TreeholeComment.Comment comment = new TreeholeComment.Comment();
        comment.setUniquecode(uniquecode);
        comment.setName(SPUtils.getString(LoginActivity.INFOS[0]));
        comment.setComment(commentText);
        comment.setImei(AppInfoUtils.getImei());
        mView.sendingCommentProgressDialog();
        mModel.sendComment(comment, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissProgressDialog();
                mView.succeededInSending();
            }
        });
    }

    public void doLoadingComments(String uniquecode) {
        mView.loadingCommentProgressDialog();
        mModel.doLoadingComments(uniquecode, new Subscriber<TreeholeComment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissProgressDialog();
                mView.netError();
                Log.e("tag", e.toString());
            }

            @Override
            public void onNext(TreeholeComment comment) {
                mView.dismissProgressDialog();
                mView.refreshView(comment);
            }
        });
    }
}
