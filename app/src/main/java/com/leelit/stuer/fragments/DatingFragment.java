package com.leelit.stuer.fragments;

import android.content.Intent;

import com.leelit.stuer.MyOrderActivity;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.DateAdapter;
import com.leelit.stuer.constant.MyOrderActivityConstant;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.GsonUtils;

/**
 * Created by Leelit on 2016/1/17.
 */
public class DatingFragment extends BaseInfoBusinessFragment {


    @Override
    BaseListAdapter bindAdapter() {
        return new DateAdapter(mList);
    }

    @Override
    String getQueryAddress() {
        return NetConstant.getDateTypeQueryAddress();
    }

    @Override
    void bindResponseGson(String jsonArray) {
        mList.addAll(GsonUtils.fromDateJsonArray(jsonArray));
    }


    @Override
    String bindPostAddress() {
        return NetConstant.DATE_CREATE;
    }

    @Override
    void bindAfterPost() {
        Intent intent = new Intent(getActivity(), MyOrderActivity.class);
        intent.putExtra(MyOrderActivityConstant.TAG, MyOrderActivityConstant.DATE);
        startActivity(intent);
    }


}
