package com.leelit.stuer.module_baseinfo.carpool;

import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_presenter.IMyBaseInfoPresenter;
import com.leelit.stuer.base_presenter.MyBaseInfoFragment;
import com.leelit.stuer.module_baseinfo.carpool.presenter.MyCarpoolPresenter;

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
