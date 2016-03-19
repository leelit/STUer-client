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
    private MySellPresenter mPresenter = new MySellPresenter(this);

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected BaseListAdapter bindAdapter() {
        return new MySellAdapter(mList);
    }

    @Override
    public void noData() {
        toast(getResources().getString(R.string.toast_no_data));
    }

    @Override
    public void netError() {
        toast(getResources().getString(R.string.net_error));
    }

    @Override
    protected void refreshTask() {
        mPresenter.doLoadingData(AppInfoUtils.getImei());
    }

    @Override
    protected void onItemClickEvent(View view, int position) {
        mPresenter.doOfflineSell(mList.get(position).getUniquecode(), position);
    }

    @Override
    public void offlineSell(int position) {
        mAdapter.removeData(position);
    }

    @Override
    public void showOffSellProgressDialog() {
        ProgressDialogUtils.showProgressDialog(getContext(), "加载中...");
    }

    @Override
    public void dismissOffSellProgressDialog() {
        ProgressDialogUtils.dismissProgressDialog();
    }


    @Override
    public void showData(List<SellInfo> sellInfos) {
        mList.clear();
        mList.addAll(sellInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}
