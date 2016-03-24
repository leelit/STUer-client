package com.leelit.stuer.module_treehole;

import android.view.View;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_fragments.BaseListFragment;
import com.leelit.stuer.bean.TreeholeInfo;

import java.util.ArrayList;
import java.util.Collections;
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
    protected void refreshTask() {
        mPresenter.doLoadDataFromNet();
    }

    @Override
    protected void onItemClickEvent(View view, int position) {

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
    public void showDataFromNet(List<TreeholeInfo> treeholeInfos) {
        mList.clear();
        Collections.reverse(treeholeInfos);
        mList.addAll(treeholeInfos);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoDataFromNet() {
        toast("没有新的数据，请稍后再来...");
    }
}
