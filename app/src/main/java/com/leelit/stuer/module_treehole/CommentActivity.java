package com.leelit.stuer.module_treehole;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.bean.TreeholeLocalInfo;
import com.leelit.stuer.dao.TreeholeDao;
import com.leelit.stuer.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommentActivity extends AppCompatActivity implements ICommentView {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.treeholeView)
    TreeholeView mTreeholeView;
    @InjectView(R.id.likePic)
    ImageView mLikePic;
    @InjectView(R.id.likeCount)
    TextView mLikeCount;
    @InjectView(R.id.unlikePic)
    ImageView mUnlikePic;
    @InjectView(R.id.unlikeCount)
    TextView mUnlikeCount;
    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.likeLayout)
    LinearLayout mLikeLayout;
    @InjectView(R.id.unlikeLayout)
    LinearLayout mUnlikeLayout;
    @InjectView(R.id.text)
    EditText mText;
    @InjectView(R.id.send)
    ImageView mSend;
    @InjectView(R.id.nocomment)
    TextView mNoComment;


    private boolean isLike;
    private boolean isUnlike;
    private boolean mOriginalIsLike;
    private boolean mOriginalIsUnlike;

    private TreeholeLocalInfo mTreeholeLocalInfo;
    private CommentPresenter mPresenter = new CommentPresenter(this);

    private List<TreeholeComment.Comment> mList = new ArrayList<>();
    private CommentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);
        initToolBar();
        mTreeholeLocalInfo = TreeholeDao.getComment(getIntent().getStringExtra("uniquecode"));
        initTreeholeView();
        initRecyclerView();
        initLikeAndUnlike();
        initSend();
        loadingComments();
    }

    private void loadingComments() {
        mPresenter.doLoadingComments(mTreeholeLocalInfo.getUniquecode());
    }

    private void initRecyclerView() {
        mAdapter = new CommentAdapter(mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSend() {
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = mText.getEditableText().toString();
                if (TextUtils.isEmpty(comment)) {
                    Toast.makeText(CommentActivity.this, "请输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.doSendComment(mTreeholeLocalInfo.getUniquecode(), comment);
            }
        });
    }

    private void initLikeAndUnlike() {
        isLike = mTreeholeLocalInfo.isLike();
        isUnlike = mTreeholeLocalInfo.isUnlike();
        mOriginalIsLike = isLike;
        mOriginalIsUnlike = isUnlike;
        if (isLike) {
            mLikePic.setImageResource(R.drawable.module_treehole_like_true);
        }
        if (isUnlike) {
            mUnlikePic.setImageResource(R.drawable.module_treehole_unlike_true);
        }

        mLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    mLikePic.setImageResource(R.drawable.module_treehole_like_normal);
                    int count = Integer.parseInt(mLikeCount.getText().toString()) - 1;
                    mLikeCount.setText(String.valueOf(count));
                } else {
                    mLikePic.setImageResource(R.drawable.module_treehole_like_true);
                    int count = Integer.parseInt(mLikeCount.getText().toString()) + 1;
                    mLikeCount.setText(String.valueOf(count));
                }
                isLike = !isLike;
            }
        });

        mUnlikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUnlike) {
                    mUnlikePic.setImageResource(R.drawable.module_treehole_unlike_normal);
                    int count = Integer.parseInt(mUnlikeCount.getText().toString()) - 1;
                    mUnlikeCount.setText(String.valueOf(count));
                } else {
                    mUnlikePic.setImageResource(R.drawable.module_treehole_unlike_true);
                    int count = Integer.parseInt(mUnlikeCount.getText().toString()) + 1;
                    mUnlikeCount.setText(String.valueOf(count));
                }
                isUnlike = !isUnlike;
            }
        });
    }

    private void initTreeholeView() {
        mTreeholeView.setTime(mTreeholeLocalInfo.getDatetime());
        mTreeholeView.setState(mTreeholeLocalInfo.getState());
        mTreeholeView.setPicture(mTreeholeLocalInfo.getPicAddress());
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("评论");
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void sendingCommentProgressDialog() {
        ProgressDialogUtils.show(this, "发送中...");
    }

    @Override
    public void dismissProgressDialog() {
        ProgressDialogUtils.dismiss();
    }

    @Override
    public void succeededInSending() {
        mText.getText().clear();
        Toast.makeText(CommentActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
        loadingComments();
    }

    @Override
    public void netError() {
        Toast.makeText(CommentActivity.this, getString(R.string.toast_net_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadingCommentProgressDialog() {
        ProgressDialogUtils.show(this, "加载中...");
    }

    @Override
    public void refreshLikeAndUnlike(TreeholeComment comment) {
        mLikeCount.setText(String.valueOf(comment.getLikeCount()));
        mUnlikeCount.setText(String.valueOf(comment.getUnlikeCount()));
    }

    @Override
    public void noComment() {
        mNoComment.setVisibility(View.VISIBLE);
    }

    @Override
    public void showComments(List<TreeholeComment.Comment> comments) {
        mNoComment.setVisibility(View.GONE);
        mList.clear();
        mList.addAll(comments);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 不能用户每次瞎点都网络请求，只在退出时才去进行
        if (isLike != mOriginalIsLike) {
            mPresenter.doLikeJob(mTreeholeLocalInfo.getUniquecode(), isLike);
        }
        if (isUnlike != mOriginalIsUnlike) {
            mPresenter.doUnlikeJob(mTreeholeLocalInfo.getUniquecode(), isUnlike);
        }
        mPresenter.doClear();
    }
}
