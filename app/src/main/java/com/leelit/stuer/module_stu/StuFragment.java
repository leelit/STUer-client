package com.leelit.stuer.module_stu;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_fragments.BaseListFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Leelit on 2016/3/3.
 */
public class StuFragment extends BaseListFragment {

    private List<String> mTitles;
    private List<String> mAddress;

    @Override
    protected BaseListAdapter bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(layoutManager);

        mTitles = Arrays.asList("办公自动化(内网)", "学分制系统(内网)", "MySTU", "邮箱", "水电管理中心(内网)", "图书馆", "校内常用电话", "汕大百事通");

        mAddress = new ArrayList<>();
        mAddress.add("http://notes.stu.edu.cn/login/Login.jsp");
        mAddress.add("http://credit.stu.edu.cn/web/");
        mAddress.add("https://sso.stu.edu.cn/login");
        mAddress.add("http://webmail.stu.edu.cn/");
        mAddress.add("https://power.stu.edu.cn/index.htm");
        mAddress.add("http://www.lib.stu.edu.cn/");
        mAddress.add("http://d.stulip.org/sp/tell.html");
        mAddress.add("http://d.stulip.org/");
        return new StuAdapter(mTitles);
    }

    @Override
    public void taskAfterLoaded() {
        // 禁止刷新
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void refreshTask() {
        // donothing
    }

    @Override
    protected void onItemClickEvent(View view, int position) {
        Intent intent = new Intent(getActivity(), StuWebViewActivity.class);
        intent.putExtra("title", mTitles.get(position));
        intent.putExtra("website", mAddress.get(position));
        startActivity(intent);
    }
}
