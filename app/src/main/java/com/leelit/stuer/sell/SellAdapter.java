package com.leelit.stuer.sell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.adapters.BaseListAdapter;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.model.SupportUtils;
import com.leelit.stuer.utils.ScreenUtils;
import com.leelit.stuer.utils.TimeUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellAdapter extends BaseListAdapter<SellAdapter.ViewHolder> {

    private Context mContext;
    private List<SellInfo> mList;

    public SellAdapter(List<SellInfo> list) {
        mList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_sell, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SellInfo sellInfo = mList.get(position);
        holder.mName.setText(sellInfo.getName());
        holder.mTime.setText(TimeUtils.compareNowWithBefore(sellInfo.getDatetime()));
        holder.mState.setText(sellInfo.getState());
        holder.mStatus.setText(sellInfo.getStatus());
        final String picAddress = sellInfo.getPicAddress();
        if (picAddress.equals("empty")) {
            holder.mPhoto.setVisibility(View.GONE);
            holder.mPhoto.setClickable(false);
        } else {
            holder.mPhoto.setVisibility(View.VISIBLE);
            holder.mPhoto.setClickable(true);
            Picasso.with(mContext)
                    .load(SupportUtils.HOST + "picture/" + picAddress + ".jpg")
                    .resize(ScreenUtils.dp2px(100f), ScreenUtils.dp2px(100f))
                    .centerCrop()
                    .into(holder.mPhoto);
            holder.mPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, PhotoActivity.class);
                    intent.putExtra("picName", picAddress);
                    mContext.startActivity(intent);
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
     * This class contains all butterknife-injected Views & Layouts from layout file 'recycler_sell.xml'
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
        @InjectView(R.id.status)
        TextView mStatus;
        @InjectView(R.id.photo)
        ImageView mPhoto;
        @InjectView(R.id.cardView)
        CardView mCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
