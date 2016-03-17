package com.leelit.stuer.fragments;

import android.content.Intent;

import com.leelit.stuer.MainActivity;
import com.leelit.stuer.MyOrderActivity;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.DateAdapter;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.constant.MyOrderActivityConstant;
import com.leelit.stuer.presenter.BaseInfoDatePresenter;

/**
 * Created by Leelit on 2016/1/17.
 */
public class DatingFragment extends BaseInfoBusinessFragment {


    private BaseInfoDatePresenter mDatePresenter = new BaseInfoDatePresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new DateAdapter(mList);
    }

    @Override
    protected void refreshTask() {
        mDatePresenter.doLoadingInfos(String.valueOf(MainActivity.mTabValue));
    }

    @Override
    protected void postInfo() {
        mDatePresenter.doPostInfo((DatingInfo) guest);
    }

    @Override
    public void afterPostInfo() {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        intent.putExtra(MyOrderActivityConstant.TAG, MyOrderActivityConstant.DATE);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatePresenter.doClear();
    }
}
