package com.leelit.stuer.sell;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leelit.stuer.R;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.fragments.BaseListFragment;
import com.leelit.stuer.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellFragment extends BaseListFragment implements ISellView {

    private SellPresenter mSellPresenter = new SellPresenter(this);
    private List<SellInfo> mList = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private SellAdapter mSellAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("加载中");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected BaseListAdapter bindAdapter() {
        mSellAdapter = new SellAdapter(mList);
        return mSellAdapter;
    }

    @Override
    public void taskAfterLoaded() {
        mSellPresenter.doLoadFromDb();
    }

    @Override
    public void showNoInfosPleaseWait() {
        toast("没有新的数据，请稍后再来...");
    }

    @Override
    public void showNoInfosPleaseRefresh() {
        toast("没有数据，请刷新...");
    }

    @Override
    protected void refreshTask() {
        mSellPresenter.doQueryList();
    }

    @Override
    protected void onItemClickEvent(View view, int position) {

    }

    @Override
    public void netError() {
        toast(getActivity().getString(R.string.net_error));
    }

    @Override
    public void showFromLoadDbInfos(List<SellInfo> sellInfos) {
        mList.clear();
        mList.addAll(sellInfos);
        mSellAdapter.notifyDataSetChanged();
    }

    @Override
    public void notRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(getActivity(), "加载中...");
    }

    @Override
    public void dismissLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }

    @Override
    public void showFormRefreshInfos(List<SellInfo> sellInfos) {
        Collections.reverse(mList); // loadFromDb展示后的时间顺序是 4 3 2 1， reverse后 1 2 3 4
        mList.addAll(sellInfos);    // 加入5 6 7 8后变成 1 2 3 4 5 6 7 8
        Collections.reverse(mList); // reverse后 8 7 6 5 4 3 2 1
        mAdapter.notifyDataSetChanged();  // 正确的时间顺序
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSellPresenter.doClear();
    }
}
