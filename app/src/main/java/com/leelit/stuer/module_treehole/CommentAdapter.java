package com.leelit.stuer.module_treehole;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.TreeholeComment;
import com.leelit.stuer.utils.TimeUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/29.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private List<TreeholeComment.Comment> mList;

    public CommentAdapter(List<TreeholeComment.Comment> list) {
        mList = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_treehole_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TreeholeComment.Comment comment = mList.get(position);
        holder.mName.setText(comment.getName());
        holder.mTime.setText(TimeUtils.compareNowWithBefore(comment.getDt()));
        holder.mComment.setText(comment.getComment());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'recycler_treehole_comment.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.name)
        TextView mName;
        @InjectView(R.id.time)
        TextView mTime;
        @InjectView(R.id.comment)
        TextView mComment;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
