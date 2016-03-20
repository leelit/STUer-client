package com.leelit.stuer.module_stu;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/3.
 */
public class StuAdapter extends BaseListAdapter<StuAdapter.ViewHolder> {

    private List<String> mTitleList;
    private int[] mIcon;

    public StuAdapter(List<String> titleList) {
        mTitleList = titleList;
        mIcon = new int[]{R.drawable.module_net_bangong, R.drawable.module_net_xuefenzhi, R.drawable.module_stu_mystu, R.drawable.module_net_youxiang, R.drawable.module_net_shudian, R.drawable.module_net_tushuguan, R.drawable.module_net_dianhua, R.drawable.module_net_baishitong};
    }

    @Override
    public void removeData(int position) {

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_stu, parent, false));
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextViewTitle.setText(mTitleList.get(position));
        holder.mIcon.setImageResource(mIcon[position]);
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
        return mTitleList.size();
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'recycler_stu.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.textView_title)
        TextView mTextViewTitle;
        @InjectView(R.id.icon)
        ImageView mIcon;
        @InjectView(R.id.cardView)
        CardView mCardView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }


}
