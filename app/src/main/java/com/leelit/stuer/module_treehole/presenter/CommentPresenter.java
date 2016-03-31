package com.leelit.stuer.module_treehole.presenter;

import com.leelit.stuer.LoginActivity;
import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.module_treehole.model.TreeholeModel;
import com.leelit.stuer.module_treehole.viewinterface.ICommentView;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.SPUtils;

import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/27.
 */
public class CommentPresenter implements IPresenter{

    private TreeholeModel mModel = new TreeholeModel();

    private ICommentView mView;
    private Subscriber<ResponseBody> mSubscriber1;
    private Subscriber<TreeholeComment> mSubscriber2;

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
        mSubscriber1 = new Subscriber<ResponseBody>() {
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
        };
        mModel.sendComment(comment, mSubscriber1);
    }

    public void doLoadingComments(String uniquecode) {
        mView.loadingCommentProgressDialog();
        mSubscriber2 = new Subscriber<TreeholeComment>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(TreeholeComment comment) {
                mView.dismissProgressDialog();
                mView.refreshLikeAndUnlike(comment);
                if (comment.getComments().isEmpty()) {
                    mView.noComment();
                } else {
                    List<TreeholeComment.Comment> comments = comment.getComments();
                    Collections.reverse(comments);
                    mView.showComments(comments);
                }
            }
        };
        mModel.doLoadingComments(uniquecode, mSubscriber2);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        if (mSubscriber2 != null) {
            mSubscriber2.unsubscribe();
        }
        mView = null;
    }
}
