package com.leelit.stuer.sell;

import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.fragments.BaseListFragment;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/18.
 */
public class MySellFragment extends BaseListFragment implements IMySellView {

    private List<SellInfo> mList = new ArrayList<>();
    private MySellPresenter mSellPresenter = new MySellPresenter(this);

    @Override
    public void notRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected BaseListAdapter bindAdapter() {
        return new MySellAdapter(mList);
    }

    @Override
    public void noInfos() {
        toast(getResources().getString(R.string.toast_no_data));
    }

    @Override
    public void netError() {
        toast(getResources().getString(R.string.net_error));
    }

    @Override
    protected void refreshTask() {
        mSellPresenter.doLoadingInfos(AppInfoUtils.getImei());
    }

    @Override
    protected void onItemClickEvent(View view, int position) {
        mSellPresenter.doOfflineTheOrder(mList.get(position).getUniquecode(),position);
    }

    @Override
    public void offlineOrder(int position) {
        mAdapter.removeData(position);
    }

    @Override
    public void showLoading() {
        ProgressDialogUtils.showProgressDialog(getContext(), "加载中...");
    }

    @Override
    public void dismissLoading() {
        ProgressDialogUtils.dismissProgressDialog();
    }


    @Override
    public void showInfos(List<SellInfo> sellInfos) {
        mList.clear();
        mList.addAll(sellInfos);
        mAdapter.notifyDataSetChanged();
    }
}
