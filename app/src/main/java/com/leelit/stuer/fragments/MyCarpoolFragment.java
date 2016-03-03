package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyCarpoolAdapter;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.GsonUtils;

import java.util.ArrayList;

/**
 * Created by Leelit on 2016/1/7.
 */
public class MyCarpoolFragment extends BaseInfoOrderFragment {


    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyCarpoolAdapter(mList);
    }

    @Override
    protected String getImeiQueryAddress() {
        return NetConstant.getCarpoolImeiQueryAddress();
    }

    @Override
    protected void bindResponseGson(String jsonArray) {
        ArrayList<ArrayList<CarpoolingInfo>> lists = GsonUtils.fromCarpoolJsonArrayArr(jsonArray);
        for (ArrayList<CarpoolingInfo> list : lists) {
            mList.add(list);
        }
    }

    @Override
    protected String getDeleteHostRecord(BaseInfo rightInfo) {
        return NetConstant.CARPOOL_EXIT + "?uniquecode=" + rightInfo.getUniquecode();
    }

    @Override
    protected String getDeleteGuestRecord(BaseInfo rightInfo) {
        return NetConstant.CARPOOL_EXIT + "?uniquecode=" + rightInfo.getUniquecode() + "&id=" + rightInfo.getId();
    }


}
