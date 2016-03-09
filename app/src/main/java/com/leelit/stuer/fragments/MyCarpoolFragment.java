package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyCarpoolAdapter;
import com.leelit.stuer.presenter.IMyOrderPresenter;
import com.leelit.stuer.presenter.MyCarpoolPresenter;

/**
 * Created by Leelit on 2016/1/7.
 */
public class MyCarpoolFragment extends MyOrderFragment {

    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyCarpoolAdapter(mList);
    }


    @Override
    protected IMyOrderPresenter bindPresenter() {
        return new MyCarpoolPresenter(this);
    }

}
