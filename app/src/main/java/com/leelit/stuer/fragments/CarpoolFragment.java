package com.leelit.stuer.fragments;

import android.content.Intent;

import com.leelit.stuer.MyBusinessActivity;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.CarpoolAdapter;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.presenter.CarpoolPresenter;

/**
 * Created by Leelit on 2015/12/6.
 */
public class CarpoolFragment extends BaseInfoFragment {

    private CarpoolPresenter mCarpoolPresenter = new CarpoolPresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new CarpoolAdapter(mList);
    }

    @Override
    protected void refreshTask() {
        mCarpoolPresenter.doLoadingData();
    }

    @Override
    protected void postInfo() {
        mCarpoolPresenter.doPostData((CarpoolingInfo) guest);
    }

    @Override
    public void doAfterJoinSuccessfully() {
        Intent intent = new Intent(getActivity(), MyBusinessActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCarpoolPresenter.doClear();
    }
}
