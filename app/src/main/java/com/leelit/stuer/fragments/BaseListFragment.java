package com.leelit.stuer.fragments;

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
import com.leelit.stuer.adapters.BaseListAdapter;

/**
 * Created by Leelit on 2015/12/6.
 */
public abstract class BaseListFragment extends Fragment {

    protected RecyclerView mRecyclerView;

    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected BaseListAdapter mAdapter;

    abstract int bindInflateRes();

    abstract BaseListAdapter bindAdapter();

    abstract void refreshTask();

    abstract void onItemClickEvent(View view, int position);


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(bindInflateRes(), container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        mAdapter = bindAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                onItemClickEvent(view, position);
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTask();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                refreshTask();
            }
        });
    }

    void toast(String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
    }

}
