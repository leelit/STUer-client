package com.leelit.stuer.base_view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;

/**
 * Created by Leelit on 2015/12/6.
 * <p/>
 * only contains a SwipeRefreshLayout & RecyclerView
 */
public abstract class BaseListFragment extends Fragment {

    protected RecyclerView mRecyclerView;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected BaseListAdapter mAdapter;

    protected abstract BaseListAdapter bindAdapter();

    protected abstract void refreshTask();

    protected abstract void onItemClickEvent(View view, int position);


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_list, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        // 默认布局
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mAdapter = bindAdapter();
        mRecyclerView.setAdapter(mAdapter);

        // 将adapter的click task转移到fragment中
        if (mAdapter != null) {
            mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    onItemClickEvent(view, position);
                }
            });
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTask();
            }
        });

        taskAfterLoaded();  // 默认执行下拉操作

        return view;
    }

    protected void taskAfterLoaded() {
        autoRefresh();
    }

    public void autoRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                refreshTask();
            }
        });
    }


    public void toast(String text) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
            // 此处不设null，如果某个Fragment有数据时刷新，在数据还未加载完毕时切换到其他fragment，会产生Fragment重叠现象
            mRecyclerView.setAdapter(null);
        }
    }

    public void smoothToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }
}
