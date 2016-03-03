package com.leelit.stuer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.leelit.stuer.constant.MyOrderActivityConstant;
import com.leelit.stuer.fragments.MyCarpoolFragment;
import com.leelit.stuer.fragments.MyDateFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyOrderActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.content)
    FrameLayout mContent;

    private int mOrderActivityConstant;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.inject(this);

        mOrderActivityConstant = getIntent().getIntExtra(MyOrderActivityConstant.TAG, 1);

        if (mOrderActivityConstant == MyOrderActivityConstant.CARPOOL) {
            mToolbar.setTitle(getString(R.string.mine_title_1));
            mFragment = new MyCarpoolFragment();
        }else if (mOrderActivityConstant == MyOrderActivityConstant.DATE) {
            mToolbar.setTitle(getString(R.string.mine_title_2));
            mFragment = new MyDateFragment();
        }

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content, mFragment).
                commit();

    }

}
