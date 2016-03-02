package com.leelit.stuer.adapters;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/1/8.
 */
public class MyCarpoolAdapter extends BaseListAdapter<MyCarpoolAdapter.ViewHolder> {

    private Context mContext;
    private List<List<? extends BaseInfo>> mLists;


    public MyCarpoolAdapter(List<List<? extends BaseInfo>> lists) {
        mLists = lists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_mine_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final List<? extends BaseInfo> list = mLists.get(position);

        holder.mTextViewRoute.setText(((CarpoolingInfo) list.get(0)).getRoute());
        holder.mTextViewTiming.setText(list.get(0).getDate() + "  " + list.get(0).getTime());
        holder.mLinearLayout.removeAllViews();

        // fixed by placeholder //avoid reuse problem
        for (int i = 0; i < list.size(); i++) {
            CarpoolingInfo info = (CarpoolingInfo) list.get(i);
            TextView textView = getTextView(info, holder.mLinearLayout);
            holder.mLinearLayout.addView(textView);
        }

        if (mOnItemClickListener != null) {
            holder.mControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });
        }
    }

    @NonNull
    private TextView getTextView(final CarpoolingInfo info, LinearLayout linearLayout) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.recycler_mine_order_friends, linearLayout, false);
        if (info.getFlag().equals("host")) {
            String text = "拼主:" + info.getName() + " 已有:" + info.getTemporaryCount();
            textView.setText(text);
            textView.setBackgroundResource(R.drawable.carpool_tv_confirm_host);
        } else {
            String text = "拼客:" + info.getName() + " 已有:" + info.getTemporaryCount();
            textView.setText(text);
        }
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                final String[] items = {"拨打长号", "拨打短号", "发送短信", "复制微信号"};
                AlertDialog dialog = builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + info.getTel()));
                                mContext.startActivity(intent1);
                                break;
                            case 1:
                                if (info.getShortTel().equals("无")) {
                                    Toast.makeText(mContext, "对方无短号", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + info.getShortTel()));
                                    mContext.startActivity(intent2);
                                }
                                break;
                            case 2:
                                Intent intent3 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + info.getTel()));
                                mContext.startActivity(intent3);
                                break;
                            case 3:
                                ClipboardManager myClipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData myClip = ClipData.newPlainText("text", info.getWechat());
                                myClipboard.setPrimaryClip(myClip);
                                Toast.makeText(mContext, "已复制微信号：" + info.getWechat(), Toast.LENGTH_SHORT).show();
                                break;
                        }
                        dialog.dismiss();
                    }
                }).create();
                dialog.show();
            }
        });
        return textView;
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


    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.textView_route)
        TextView mTextViewRoute;
        @InjectView(R.id.textView_timing)
        TextView mTextViewTiming;
        @InjectView(R.id.control)
        ImageButton mControl;
        @InjectView(R.id.linearLayout_place_holder)
        LinearLayout mLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
