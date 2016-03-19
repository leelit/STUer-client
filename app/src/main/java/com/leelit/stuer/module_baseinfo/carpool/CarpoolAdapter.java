package com.leelit.stuer.module_baseinfo.carpool;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2015/12/6.
 */
public class CarpoolAdapter extends BaseListAdapter<CarpoolAdapter.ViewHolder> {

    private List<BaseInfo> mList;

    public CarpoolAdapter(List<BaseInfo> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_carpool, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CarpoolingInfo info = (CarpoolingInfo) mList.get(position);
        holder.mTextViewRoute.setText(info.getRoute());
        holder.mTextViewTiming.setText(info.getDate() + "  " + info.getTime());
        holder.mTextViewHost.setText("拼主：" + info.getName());
        holder.mTextViewCount.setText("已有：" + info.getTemporaryCount());
        if (mOnItemClickListener != null) {
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void removeData(int position) {

    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'recycler_carpool.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.textView_route)
        TextView mTextViewRoute;
        @InjectView(R.id.textView_timing)
        TextView mTextViewTiming;
        @InjectView(R.id.textView_host)
        TextView mTextViewHost;
        @InjectView(R.id.textView_count)
        TextView mTextViewCount;
        @InjectView(R.id.cardView)
        CardView mCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }


//    public void addData(int position, String[] data) {
//        int length = data.length;
//        for (int i = 0; i < length; i++) {
//            mList.add(position + i, data[i]);
//        }
//        notifyItemRangeInserted(position, length);
//    }
//
//    public void addData(int position, String data) {
//        mList.add(position, data);
//        notifyItemInserted(position);
//    }
//
//    public void removeData(int position) {
//        mList.remove(position);
//        notifyItemRemoved(position);
//    }


}
