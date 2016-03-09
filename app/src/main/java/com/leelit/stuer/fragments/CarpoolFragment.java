package com.leelit.stuer.fragments;

import android.content.Intent;

import com.leelit.stuer.MyOrderActivity;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.CarpoolAdapter;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.presenter.BaseInfoCarpoolPresenter;

/**
 * Created by Leelit on 2015/12/6.
 */
public class CarpoolFragment extends BaseInfoBusinessFragment {

    private BaseInfoCarpoolPresenter mCarpoolPresenter = new BaseInfoCarpoolPresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new CarpoolAdapter(mList);
    }

    @Override
    protected void refreshTask() {
        mCarpoolPresenter.doLoadingInfos();
    }

    @Override
    protected void postInfo() {
        mCarpoolPresenter.doPostInfo((CarpoolingInfo) guest);
    }

    @Override
    public void afterPostInfo() {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        startActivity(intent);
    }
}
