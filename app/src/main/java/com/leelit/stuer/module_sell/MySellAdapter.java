package com.leelit.stuer.module_sell;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.dao.SellDao;
import com.leelit.stuer.utils.SupportModelUtils;
import com.leelit.stuer.utils.ScreenUtils;
import com.leelit.stuer.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/18.
 */
public class MySellAdapter extends BaseListAdapter<MySellAdapter.ViewHolder> {

    private Context mContext;
    private List<SellInfo> mList;

    public MySellAdapter(List<SellInfo> list) {
        mList = list;
    }


    @Override
    public MySellAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_sell_my, parent, false));
    }

    @Override
    public void onBindViewHolder(final MySellAdapter.ViewHolder holder, int position) {
        final SellInfo sellInfo = mList.get(position);
        holder.mName.setText(sellInfo.getName());
        holder.mTime.setText(TimeUtils.compareNowWithBefore(sellInfo.getDatetime()));
        holder.mState.setText(sellInfo.getState());
        final String picAddress = sellInfo.getPicAddress();
        if (picAddress.equals("empty")) {
            holder.mPhoto.setVisibility(View.GONE);
            holder.mPhoto.setClickable(false);
        } else {
            holder.mPhoto.setVisibility(View.VISIBLE);
            holder.mPhoto.setClickable(true);
            Picasso.with(mContext)
                    .load(SupportModelUtils.HOST + "picture/" + picAddress + ".jpg")
                    .resize(ScreenUtils.dp2px(50f), ScreenUtils.dp2px(50f))
                    .centerCrop()
                    .into(holder.mPhoto);
        }
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                mOnItemClickListener.onItemClick(v,position);
            }
        });

        holder.mStatus.setText(sellInfo.getStatus());
        if (sellInfo.getStatus().equals("off")) {
            holder.mStatus.setTextColor(holder.mName.getCurrentTextColor());
        } else {
            holder.mStatus.setTextColor(Color.rgb(255, 96, 110));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void removeData(int position) {
        mList.get(position).setStatus("off");
        new SellDao().updateStatusInSell(mList.get(position));
        notifyDataSetChanged();
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'recycler_sell_my.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.name)
        TextView mName;
        @InjectView(R.id.time)
        TextView mTime;
        @InjectView(R.id.state)
        TextView mState;
        @InjectView(R.id.photo)
        ImageView mPhoto;
        @InjectView(R.id.delete)
        ImageView mDelete;
        @InjectView(R.id.cardView)
        CardView mCardView;
        @InjectView(R.id.status)
        TextView mStatus;


        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
