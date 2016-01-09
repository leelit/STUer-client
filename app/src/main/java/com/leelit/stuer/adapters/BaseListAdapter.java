package com.leelit.stuer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Leelit on 2016/1/8.
 */
public abstract class BaseListAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public abstract void removeData(int position);
}
