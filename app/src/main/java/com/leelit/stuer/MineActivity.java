package com.leelit.stuer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.leelit.stuer.fragments.MyCarpoolFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MineActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.content)
    FrameLayout mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        ButterKnife.inject(this);

        mToolbar.setTitle(getString(R.string.mine_title));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Fragment mineFragment = MyCarpoolFragment.getInstance();
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content, mineFragment).
                commit();

    }

}
