package com.leelit.stuer.module_treehole;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.bean.TreeholeInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholeAdapter extends BaseListAdapter<TreeholeAdapter.ViewHolder> {

    private List<TreeholeInfo> mList;

    public TreeholeAdapter(List<TreeholeInfo> list) {
        mList = list;
    }

    @Override
    public TreeholeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(new TreeholeView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final TreeholeAdapter.ViewHolder holder, int position) {
        TreeholeInfo info = mList.get(position);
        holder.treeholeView.setTime(info.getDatetime());
        holder.treeholeView.setState(info.getState());
        holder.treeholeView.setPicture(info.getPicAddress());
        holder.treeholeView.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void removeData(int position) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TreeholeView treeholeView;

        public ViewHolder(View itemView) {
            super(itemView);
            treeholeView = (TreeholeView) itemView;
        }
    }
}
