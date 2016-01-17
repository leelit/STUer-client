package com.leelit.stuer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leelit.stuer.constant.FragmentIndex;
import com.leelit.stuer.fragments.CarpoolFragment;
import com.leelit.stuer.fragments.DatingFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.fabBtn)
    FloatingActionButton mFabBtn;
    @InjectView(R.id.navigationView)
    NavigationView mNavigationView;
    @InjectView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;

    private MenuItem mMainMenuItem;

    private ActionBarDrawerToggle mDrawerToggle;

    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);
        initDrawer();
        initFab();
        initFragment();
    }

    private void initFragment() {
        Fragment carpoolFragment = CarpoolFragment.getInstance();
        replaceFragment(carpoolFragment);
        currentFragment = carpoolFragment;
    }

    private void initFab() {
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment instanceof CarpoolFragment) {
                    Intent intent = new Intent(MainActivity.this, PostInfoActivity.class);
                    intent.putExtra(FragmentIndex.TAG, FragmentIndex.CARPOOL);
                    startActivity(intent);
                } else if (currentFragment instanceof DatingFragment){
                    Intent intent = new Intent(MainActivity.this, PostInfoActivity.class);
                    intent.putExtra(FragmentIndex.TAG, FragmentIndex.DATE);
                    startActivity(intent);
                }
            }
        });
    }

    private void initDrawer() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.hello_world, R.string.hello_world);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                switchFragment(menuItem);
                return false;
            }
        });
    }

    private void switchFragment(MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        mToolbar.setTitle(title);
        if (title.equals(getString(R.string.carpool))) {
            if (currentFragment instanceof CarpoolFragment) {
                return;
            }
            mMainMenuItem.setIcon(R.drawable.pic1);
            mFabBtn.setImageResource(R.drawable.pic1);
            currentFragment = new CarpoolFragment();
            mTabLayout.setVisibility(View.GONE);
        } else if (title.equals(getString(R.string.date))) {
            if (currentFragment instanceof DatingFragment) {
                return;
            }
            mMainMenuItem.setIcon(R.drawable.pic2);
            mFabBtn.setImageResource(R.drawable.pic2);
            currentFragment = new DatingFragment();
            mTabLayout.setVisibility(View.VISIBLE);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            for (int i = 1; i <= 6; i++) {
                mTabLayout.addTab(mTabLayout.newTab().setText("Tab" + i));
            }
        }
        replaceFragment(currentFragment);

    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content, fragment).
                commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMainMenuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mine) {
            if (currentFragment instanceof CarpoolFragment) {
                Intent intent = new Intent(MainActivity.this, MineActivity.class);
                startActivity(intent);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

}
