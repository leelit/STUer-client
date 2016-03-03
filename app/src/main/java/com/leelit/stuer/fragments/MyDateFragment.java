package com.leelit.stuer.fragments;

import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyDateAdapter;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.GsonUtils;

import java.util.ArrayList;

/**
 * Created by Leelit on 2016/3/1.
 */
public class MyDateFragment extends BaseInfoOrderFragment {


    @Override
    protected BaseListAdapter bindAdapter() {
        return new MyDateAdapter(mList);
    }

    @Override
    protected String getImeiQueryAddress() {
        return NetConstant.getDateImeiQueryAddress();
    }

    @Override
    protected void bindResponseGson(String jsonArray) {
        ArrayList<ArrayList<DatingInfo>> lists = GsonUtils.fromDateJsonArrayArr(jsonArray);
        for (ArrayList<DatingInfo> list : lists) {
            mList.add(list);
        }
    }

    @Override
    protected String getDeleteHostRecord(BaseInfo rightInfo) {
        return NetConstant.DATE_EXIT + "?uniquecode=" + rightInfo.getUniquecode();
    }

    @Override
    protected String getDeleteGuestRecord(BaseInfo rightInfo) {
        return NetConstant.DATE_EXIT + "?uniquecode=" + rightInfo.getUniquecode() + "&id=" + rightInfo.getId();
    }


}
