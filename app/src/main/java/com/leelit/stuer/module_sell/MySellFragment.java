package com.leelit.stuer.module_sell;

import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_view.BaseListFragment;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.module_sell.presenter.MySellPresenter;
import com.leelit.stuer.module_sell.viewinterface.IMySellView;
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
    private ArrayList<Integer> mUpdate = new ArrayList<>();

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
    public void doAfterOfflineSell(int position) {
        mAdapter.notifyItemChanged(position); // 局部刷新MySellFrament
    }

    @Override
    public List<SellInfo> getCurrentList() {
        return mList;
    }

    @Override
    public void showOffSellProgressDialog() {
        ProgressDialogUtils.show(getContext(), "商品下线中...");
    }

    @Override
    public void dismissOffSellProgressDialog() {
        ProgressDialogUtils.dismiss();
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
