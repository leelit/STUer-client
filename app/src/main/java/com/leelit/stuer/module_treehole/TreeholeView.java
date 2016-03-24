package com.leelit.stuer.module_treehole;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.module_sell.PhotoActivity;
import com.leelit.stuer.utils.ScreenUtils;
import com.leelit.stuer.utils.SupportModelUtils;
import com.leelit.stuer.utils.TimeUtils;
import com.squareup.picasso.Picasso;

/**
 * Created by Leelit on 2016/3/24.
 */
public class TreeholeView extends RelativeLayout {

    TextView master;
    TextView mTime;
    TextView mState;
    ImageView mPhoto;
    CardView mCardView;

    private Context mContext;

    public TreeholeView(Context context) {
        this(context, null);
    }

    public TreeholeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeholeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.recycler_treehole, this);
        mTime = (TextView) findViewById(R.id.time);
        mState = (TextView) findViewById(R.id.state);
        mPhoto = (ImageView) findViewById(R.id.photo);
        mCardView = (CardView) findViewById(R.id.cardView);
        master = (TextView) findViewById(R.id.master);
    }

    public void setTime(String datetime) {
        mTime.setText(TimeUtils.compareNowWithBefore(datetime));
    }

    public void setState(String state) {
        if (state.length() >= 8 && state.substring(0, 8).equals("@master@")) {
            StringBuilder sb = new StringBuilder(state);
            sb.delete(0, 8);
            mState.setText(sb.toString());
            master.setVisibility(View.VISIBLE);
        } else {
            mState.setText(state);
            master.setVisibility(View.GONE);
        }
    }

    public void setPicture(final String picAddress) {
        if (picAddress.equals("empty")) {
            mPhoto.setVisibility(View.GONE);
            mPhoto.setClickable(false);
            return;
        }
        mPhoto.setVisibility(View.VISIBLE);
        mPhoto.setClickable(true);
        Picasso.with(mContext)
                .load(SupportModelUtils.HOST + "picture/" + picAddress + ".jpg")
                .resize(ScreenUtils.dp2px(75f), ScreenUtils.dp2px(75f))
                .centerCrop()
                .into(mPhoto);
        mPhoto.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PhotoActivity.class);
                intent.putExtra("picName", picAddress);
                mContext.startActivity(intent);
            }
        });
    }
}
