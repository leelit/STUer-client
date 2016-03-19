package com.leelit.stuer.base_presenter;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.base_fragments.BaseListFragment;
import com.leelit.stuer.base_fragments.viewinterface.IMyBaseInfoView;
import com.leelit.stuer.utils.AppInfoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leelit on 2016/3/2.
 */
public abstract class MyBaseInfoFragment extends BaseListFragment implements IMyBaseInfoView {

    protected List<List<? extends BaseInfo>> mList = new ArrayList<>();
    private ProgressDialog mProgressDialog;

    private IMyBaseInfoPresenter mPresenter;

    protected abstract IMyBaseInfoPresenter bindPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mPresenter = bindPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void refreshTask() {
        mPresenter.doLoadingData(AppInfoUtils.getImei());
    }

    @Override
    protected void onItemClickEvent(View view, int position) {
        // 找到自己的info
        List<? extends BaseInfo> relativeInfos = mList.get(position);
        BaseInfo rightInfo = null;
        for (int i = 0; i < relativeInfos.size(); i++) {
            BaseInfo current = relativeInfos.get(i);
            if (current.getImei().equals(AppInfoUtils.getImei())) {
                rightInfo = current;
            }
        }
        // host解散，guest离开
        if (rightInfo != null) {
            if (rightInfo.getFlag().equals("host")) {
                finishThisOrder(rightInfo, position);
            } else {
                quitThisOrder(rightInfo, position);
            }
        }
    }


    private void finishThisOrder(BaseInfo rightInfo, int position) {
        mPresenter.doFinishOrder(rightInfo.getUniquecode(), position);
    }

    private void quitThisOrder(BaseInfo rightInfo, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("uniquecode", rightInfo.getUniquecode());
        map.put("id", String.valueOf(rightInfo.getId()));
        mPresenter.doQuitOrder(map, position);
    }


    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showData(List<List<? extends BaseInfo>> lists) {
        mList.clear();
        mList.addAll(lists);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void noData() {
        toast(getResources().getString(R.string.toast_no_data));
    }

    @Override
    public void netError() {
        toast(getResources().getString(R.string.toast_net_error));
    }

    @Override
    public void showDeleteProgressDialog(String message) {
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    @Override
    public void dismissDeleteProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void deleteOrder(int position) {
        mAdapter.removeData(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}