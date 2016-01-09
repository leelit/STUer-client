package com.leelit.stuer.fragments;

import android.support.annotation.NonNull;
import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.MyCarpoolAdapter;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.GsonUtils;
import com.leelit.stuer.utils.OkHttpUtils;
import com.leelit.stuer.utils.PhoneInfoUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/1/7.
 */
public class MyCarpoolFragment extends BaseListFragment {

    private List<List<CarpoolingInfo>> mList = new ArrayList<>();

    public static MyCarpoolFragment getInstance() {
        MyCarpoolFragment myCarpoolFragment = new MyCarpoolFragment();
        return myCarpoolFragment;
    }

    @Override
    int bindInflateRes() {
        return R.layout.fragment_base_list;
    }

    @Override
    BaseListAdapter bindAdapter() {
        return new MyCarpoolAdapter(mList);
    }

    @Override
    void refreshTask() {
        OkHttpUtils.get(NetConstant.getImeiQueryAddress(), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                            toast("网络出错...");
                        }
                    });
                }
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (getActivity() != null) {
                    String jsonArray = response.body().string();
                    mList.clear();
                    ArrayList<ArrayList<CarpoolingInfo>> lists = GsonUtils.fromJsonArrayArr(jsonArray);
                    for (ArrayList<CarpoolingInfo> list : lists) {
                        mList.add(list);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSwipeRefreshLayout.setRefreshing(false);
                            mAdapter.notifyDataSetChanged();
                            if (mList.isEmpty()) {
                                toast("没有数据...");
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    void onItemClickEvent(View view, int position) {
        List<CarpoolingInfo> relativeInfos = mList.get(position);
        CarpoolingInfo rightInfo = null;
        for (int i = 0; i < relativeInfos.size(); i++) {
            CarpoolingInfo current = relativeInfos.get(i);
            if (current.getImei().equals(PhoneInfoUtils.getImei())) {
                rightInfo = current;
            }
        }
        if (rightInfo != null) {
            if (rightInfo.getFlag().equals("host")) {
                finishThisOrder(rightInfo, position);
            } else {
                quitThisOrder(rightInfo, position);
            }
        }
    }

    private void finishThisOrder(CarpoolingInfo rightInfo, final int position) {
        String deleteAddress = getDeleteHostRecord(rightInfo);
        OkHttpUtils.getOnUiThread(deleteAddress, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                toast("网络出错...");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                toast("完成");
                mAdapter.removeData(position);

            }
        }, getActivity());
    }


    private void quitThisOrder(CarpoolingInfo rightInfo, final int position) {
        String deleteAddress = getDeleteGuestRecord(rightInfo);
        OkHttpUtils.getOnUiThread(deleteAddress, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                toast("网络出错...");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                toast("退出成功");
                mAdapter.removeData(position);
            }
        }, getActivity());
    }

    private String getDeleteHostRecord(CarpoolingInfo rightInfo) {
        return NetConstant.CARPOOL_EXIT + "?uniquecode=" + rightInfo.getUniquecode();
    }

    @NonNull
    private String getDeleteGuestRecord(CarpoolingInfo rightInfo) {
        return NetConstant.CARPOOL_EXIT + "?uniquecode=" + rightInfo.getUniquecode() + "&id=" + rightInfo.getId();
    }


}
