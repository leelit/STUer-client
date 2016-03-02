package com.leelit.stuer.fragments;

import android.content.Intent;

import com.leelit.stuer.MyOrderActivity;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.CarpoolAdapter;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.GsonUtils;

/**
 * Created by Leelit on 2015/12/6.
 */
public class CarpoolFragment extends BaseInfoBusinessFragment {


    @Override
    BaseListAdapter bindAdapter() {
        return new CarpoolAdapter(mList);
    }


    @Override
    String getQueryAddress() {
        return NetConstant.CARPOOL_QUERY;
    }

    @Override
    void bindResponseGson(String jsonArray) {
        mList.addAll(GsonUtils.fromJsonArray(jsonArray));
    }


    String bindPostAddress() {
        return NetConstant.CARPOOL_CREATE;
    }

    void bindAfterPost() {
        startActivity(new Intent(getActivity(), MyOrderActivity.class));
    }


}
