package com.leelit.stuer.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.view.View;

import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.common.SharedCreation;
import com.leelit.stuer.utils.OkHttpUtils;
import com.leelit.stuer.utils.PhoneInfoUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/2.
 */
public abstract class BaseInfoOrderFragment extends BaseListFragment {

    protected List<List<? extends BaseInfo>> mList = new ArrayList<>();
    protected List<Call> mCalls = new ArrayList<>();

    protected abstract String getImeiQueryAddress();

    protected abstract void bindResponseGson(String jsonArray);

    protected abstract String getDeleteHostRecord(BaseInfo rightInfo);

    protected abstract String getDeleteGuestRecord(BaseInfo rightInfo);

    @Override
    void refreshTask() {
        // 处理数据，这里用同步Utils
        Call call = OkHttpUtils.get(getImeiQueryAddress(), new Callback() {
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
                    bindResponseGson(jsonArray);
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
        mCalls.add(call);
    }

    @Override
    void onItemClickEvent(View view, int position) {
        List<? extends BaseInfo> relativeInfos = mList.get(position);
        BaseInfo rightInfo = null;
        for (int i = 0; i < relativeInfos.size(); i++) {
            BaseInfo current = relativeInfos.get(i);
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

    private void finishThisOrder(BaseInfo rightInfo, final int position) {
        final ProgressDialog quickDialog = SharedCreation.createDialog(getActivity(), "结束中...", AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        quickDialog.show();
        String deleteAddress = getDeleteHostRecord(rightInfo);
        Call call = OkHttpUtils.getOnUiThread(deleteAddress, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (!mCalls.get(0).isCanceled()) {
                    toast("网络出错...");
                }
                quickDialog.dismiss();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                toast("完成");
                quickDialog.dismiss();
                mAdapter.removeData(position);
            }
        }, getActivity());
        mCalls.add(call);
    }


    private void quitThisOrder(BaseInfo rightInfo, final int position) {
        final ProgressDialog finishDialog = SharedCreation.createDialog(getActivity(), "退出中...");
        finishDialog.show();
        String deleteAddress = getDeleteGuestRecord(rightInfo);
        Call call = OkHttpUtils.getOnUiThread(deleteAddress, new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (!mCalls.get(0).isCanceled()) {
                    toast("网络出错...");
                }
                finishDialog.dismiss();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                toast("退出成功");
                finishDialog.dismiss();
                mAdapter.removeData(position);
            }
        }, getActivity());
        mCalls.add(call);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < mCalls.size(); i++) {
            mCalls.get(i).cancel();
        }
    }

}
