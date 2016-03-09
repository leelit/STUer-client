package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyDateAdapter;
import com.leelit.stuer.presenter.IMyOrderPresenter;
import com.leelit.stuer.presenter.MyDatePresenter;

/**
 * Created by Leelit on 2016/3/1.
 */
public class MyDateFragment extends MyOrderFragment {


    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyDateAdapter(mList);
    }


    @Override
    protected IMyOrderPresenter bindPresenter() {
        return new MyDatePresenter(this);
    }
}
