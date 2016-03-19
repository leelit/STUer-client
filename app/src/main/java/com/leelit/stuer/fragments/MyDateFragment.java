package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyDateAdapter;
import com.leelit.stuer.presenter.IMyBaseInfoPresenter;
import com.leelit.stuer.presenter.MyDatePresenter;

/**
 * Created by Leelit on 2016/3/1.
 */
public class MyDateFragment extends MyBaseInfoFragment {


    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyDateAdapter(mList);
    }


    @Override
    protected IMyBaseInfoPresenter bindPresenter() {
        return new MyDatePresenter(this);
    }
}
