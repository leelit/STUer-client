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

public class CommentActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.treeholeView)
    TreeholeView mTreeholeView;
    @InjectView(R.id.like)
    ImageView mLike;
    @InjectView(R.id.likeCount)
    TextView mLikeCount;
    @InjectView(R.id.unlike)
    ImageView mUnlike;
    @InjectView(R.id.unlikeCount)
    TextView mUnlikeCount;
    @InjectView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @InjectView(R.id.likeLayout)
    LinearLayout mLikeLayout;
    @InjectView(R.id.unlikeLayout)
    LinearLayout mUnlikeLayout;

    private boolean isLike = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.inject(this);
        initToolBar();
        initTreeholeView();
        mLikeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike) {
                    mLike.setImageResource(R.drawable.module_treehole_like_true);
                } else {
                    mLike.setImageResource(R.drawable.module_treehole_like_normal);
                }
                isLike = !isLike;
            }
        });
    }

    private void initTreeholeView() {
        TreeholeComment comment = TreeholeDao.getComment("1319861953");
        mTreeholeView.setTime(comment.getDatetime());
        mTreeholeView.setState(comment.getState());
        mTreeholeView.setPicture(comment.getPicAddress());
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


}
