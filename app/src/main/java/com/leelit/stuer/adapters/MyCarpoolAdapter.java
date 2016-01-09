package com.leelit.stuer.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.CarpoolingInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/1/8.
 */
public class MyCarpoolAdapter extends BaseListAdapter<MyCarpoolAdapter.ViewHolder> {

    private List<List<CarpoolingInfo>> mLists;

    public MyCarpoolAdapter(List<List<CarpoolingInfo>> lists) {
        mLists = lists;
    }

    @Override
    public MyCarpoolAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_carpool_mine, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyCarpoolAdapter.ViewHolder holder, int position) {
        List<CarpoolingInfo> list = mLists.get(position);
        String result = list.get(0).getRoute() + "\n" + list.get(0).getDate() + "  " + list.get(0).getTime() + "\n\n";
        for (CarpoolingInfo info : list) {
            result += info.getId() + " " + info.getName() + " " + info.getTel() + " " + info.getWechat() + "\n";
        }
        holder.textView.setText(result);
        if (mOnItemClickListener != null) {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        return mLists.size();
    }

    @Override
    public void removeData(int position) {
        mLists.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }


}
