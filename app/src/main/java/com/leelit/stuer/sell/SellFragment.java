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
import com.leelit.stuer.stu.SellAdapter;

import java.util.ArrayList;
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
    public void refreshAfterLoaded() {
        // 不主动刷新
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
    public void showInfos(List<SellInfo> sellInfos) {
        mList.clear();
        mList.addAll(sellInfos);
        mSellAdapter.notifyDataSetChanged();
    }

    @Override
    public void notRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSellPresenter.doClear();
    }
}
