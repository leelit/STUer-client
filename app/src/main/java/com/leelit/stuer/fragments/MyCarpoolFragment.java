package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyCarpoolAdapter;
import com.leelit.stuer.presenter.IMyBaseInfoPresenter;
import com.leelit.stuer.presenter.MyCarpoolPresenter;

/**
 * Created by Leelit on 2016/1/7.
 */
public class MyCarpoolFragment extends MyBaseInfoFragment {

    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyCarpoolAdapter(mList);
    }


    @Override
    protected IMyBaseInfoPresenter bindPresenter() {
        return new MyCarpoolPresenter(this);
    }

}
