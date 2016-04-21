package com.leelit.stuer.module_baseinfo.date;

import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_presenter.IMyBaseInfoPresenter;
import com.leelit.stuer.base_view.MyBaseInfoFragment;
import com.leelit.stuer.module_baseinfo.date.presenter.MyDatePresenter;

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
