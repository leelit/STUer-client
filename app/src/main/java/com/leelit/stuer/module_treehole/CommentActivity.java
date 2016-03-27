package com.leelit.stuer.module_treehole;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.dao.TreeholeDao;

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


    private boolean isLike;
    private boolean isUnlike;
    private boolean mOriginalIsLike;
    private boolean mOriginalIsUnlike;

    private TreeholeComment mTreeholeComment;
    private CommentPresenter mPresenter = new CommentPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);
        initToolBar();
        mTreeholeComment = TreeholeDao.getComment(getIntent().getStringExtra("uniquecode"));
        initTreeholeView();
        initLikeAndUnlike();
    }

    private void initLikeAndUnlike() {
        isLike = mTreeholeComment.isLike();
        isUnlike = mTreeholeComment.isUnlike();
        mOriginalIsLike = isLike;
        mOriginalIsUnlike = isUnlike;
        if (isLike) {
            mLikePic.setImageResource(R.drawable.module_treehole_like_true);
            mLikeCount.setText("1"); // 以防网络出错
        }
        if (isUnlike) {
            mUnlikePic.setImageResource(R.drawable.module_treehole_unlike_true);
            mUnlikeCount.setText("1");
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
        mTreeholeView.setTime(mTreeholeComment.getDatetime());
        mTreeholeView.setState(mTreeholeComment.getState());
        mTreeholeView.setPicture(mTreeholeComment.getPicAddress());
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
    protected void onDestroy() {
        super.onDestroy();
        // 不能用户每次瞎点都网络请求，只在退出时才去进行
        if (isLike != mOriginalIsLike) {
            mPresenter.doLikeJob(mTreeholeComment.getUniquecode(), isLike);
        }
        if (isUnlike != mOriginalIsUnlike) {
            mPresenter.doUnlikeJob(mTreeholeComment.getUniquecode(), isUnlike);
        }
    }
}
