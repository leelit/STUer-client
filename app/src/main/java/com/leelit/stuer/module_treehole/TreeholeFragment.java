package com.leelit.stuer.module_treehole;

import android.content.Intent;
import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_view.BaseListFragment;
import com.leelit.stuer.bean.TreeholeLocalInfo;
import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.module_treehole.presenter.TreeholePresenter;
import com.leelit.stuer.module_treehole.viewinterface.ITreeholeView;
import com.leelit.stuer.utils.ProgressDialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholeFragment extends BaseListFragment implements ITreeholeView {

    private List<TreeholeInfo> mList = new ArrayList<>();
    private TreeholePresenter mPresenter = new TreeholePresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new TreeholeAdapter(mList);
    }


    @Override
    protected void taskAfterLoaded() {
        mPresenter.doLoadDataFromDb();
    }

    @Override
    protected void refreshTask() {
        mPresenter.doLoadDataFromNet();
    }

    @Override
    protected void onItemClickEvent(View view, int position) {
        Intent intent = new Intent(getContext(), CommentActivity.class);
        intent.putExtra("uniquecode", mList.get(position).getUniquecode());
        startActivity(intent);
    }

    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void netError() {
        toast(getActivity().getString(R.string.net_error));
    }

    @Override
    public void showDataFromDb(List<TreeholeLocalInfo> infos) {
        mList.clear();
        mList.addAll(infos);
        mAdapter.notifyDataSetChanged();
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
    public void showNoDataInDb() {
        toast("没有缓存数据，请刷新...");
    }


    @Override
    public void showDataFromNet(List<TreeholeInfo> treeholeInfos) {
        mList.clear();
        mList.addAll(treeholeInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoDataFromNet() {
        toast("没有新的数据，请稍后再来...");
    }

    @Override
    public List<TreeholeInfo> getCurrentList() {
        return mList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}
