package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyDateAdapter;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.presenter.MyDatePresenter;
import com.leelit.stuer.utils.PhoneInfoUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leelit on 2016/3/1.
 */
public class MyDateFragment extends MyOrderFragment {

    private MyDatePresenter mPresenter = new MyDatePresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyDateAdapter(mList);
    }

    @Override
    protected void refreshTask() {
        mPresenter.doLoadingInfos(PhoneInfoUtils.getImei());
    }

    @Override
    protected void finishThisOrder(BaseInfo rightInfo, int position) {
        mPresenter.doFinishOrder(rightInfo.getUniquecode(), position);
    }

    @Override
    protected void quitThisOrder(BaseInfo rightInfo, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("uniquecode", rightInfo.getUniquecode());
        map.put("id", String.valueOf(rightInfo.getId()));
        mPresenter.doQuitOrder(map, position);
    }


}
