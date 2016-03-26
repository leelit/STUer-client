package com.leelit.stuer.module_sell;

import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_fragments.BaseListFragment;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.module_sell.presenter.SellPresenter;
import com.leelit.stuer.module_sell.viewinterface.ISellView;
import com.leelit.stuer.utils.ContactUtils;
import com.leelit.stuer.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellFragment extends BaseListFragment implements ISellView {

    private SellPresenter mSellPresenter = new SellPresenter(this);
    private List<SellInfo> mList = new ArrayList<>();
    private SellAdapter mSellAdapter;


    @Override
    protected BaseListAdapter bindAdapter() {
        mSellAdapter = new SellAdapter(mList);
        return mSellAdapter;
    }

    @Override
    public void taskAfterLoaded() {
        loadDataFromDb();
    }

    public void loadDataFromDb() {
        mSellPresenter.doLoadDataFromDb();
    }
    

    @Override
    public void showNoDataFromNet() {
        toast("没有新的数据，请稍后再来...");
    }

    @Override
    public List<SellInfo> getCurrentList() {
        return mList;
    }

    @Override
    public void showNoDataInDb() {
        toast("没有缓存数据，请刷新...");
    }

    @Override
    protected void refreshTask() {
        mSellPresenter.doLoadDataFromNet();
    }

    @Override
    protected void onItemClickEvent(View view, int position) {
        mSellPresenter.doContactSeller(mList.get(position), position);
    }

    @Override
    public void netError() {
        toast(getActivity().getString(R.string.net_error));
    }

    @Override
    public void showDataFromDb(List<SellInfo> sellInfos) {
        mList.clear();
        mList.addAll(sellInfos);
        // 如果不展示下架商品，在presenter里面处理一下这个mList
        mSellPresenter.doIfNoShowOfflineSell();
        mSellAdapter.notifyDataSetChanged();
    }


    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void showLoadingDbProgressDialog() {
        ProgressDialogUtils.show(getActivity(), "加载中...");
    }

    @Override
    public void dismissLoadingDbProgressDialog() {
        ProgressDialogUtils.dismiss();
    }

    @Override
    public void showDataFromNet(List<SellInfo> sellInfos) {
        mList.clear();
        mList.addAll(sellInfos);
        mSellPresenter.doIfNoShowOfflineSell();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showGoodsOffline(int position) {
        toast("该商品已售出...");
        mList.get(position).setStatus("off");
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContactDialog(String tel, String shortel, String wechat) {
        ContactUtils.createContactDialog(getContext(), tel, shortel, wechat).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSellPresenter.doClear();
    }


}
