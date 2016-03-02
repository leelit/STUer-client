package com.leelit.stuer.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.DatingInfo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2015/12/6.
 */
public class DateAdapter extends BaseListAdapter<DateAdapter.ViewHolder> {

    private List<BaseInfo> mList;

    public DateAdapter(List<BaseInfo> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_date, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DatingInfo info = (DatingInfo) mList.get(position);
        holder.mTextViewTiming.setText(info.getDate() + "  " + info.getTime());
        holder.mTextViewHost.setText("拼主：" + info.getName());
        holder.mTextViewCount.setText("已有：" + info.getTemporaryCount());
        holder.mTextViewDescription.setText(info.getDescription());
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


    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.textView_timing)
        TextView mTextViewTiming;
        @InjectView(R.id.textView_host)
        TextView mTextViewHost;
        @InjectView(R.id.textView_count)
        TextView mTextViewCount;
        @InjectView(R.id.textView_description)
        TextView mTextViewDescription;
        @InjectView(R.id.cardView)
        CardView mCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
