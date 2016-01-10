package com.leelit.stuer.fragments;

import android.widget.Toast;

import com.leelit.stuer.R;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.adapters.CarpoolAdapter;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.utils.GsonUtils;
import com.leelit.stuer.utils.OkHttpUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2015/12/6.
 */
public abstract class BaseCarpoolFragment extends BaseListFragment {


    protected List<CarpoolingInfo> mList = new ArrayList<>();

    abstract String getQueryUrl();

    @Override
    int bindInflateRes() {
        return R.layout.fragment_base_list;
    }

    @Override
    BaseListAdapter bindAdapter() {
        return new CarpoolAdapter(mList);
    }

    @Override
    void refreshTask() {
        OkHttpUtils.get(getQueryUrl(), new Callback() {
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
                    mList.addAll(GsonUtils.fromJsonArray(jsonArray));
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


    void toast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
