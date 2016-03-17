package com.leelit.stuer.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.leelit.stuer.LoginActivity;
import com.leelit.stuer.R;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.common.SharedAnimation;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.viewinterface.IBaseInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/2.
 */
public abstract class BaseInfoBusinessFragment extends BaseListFragment implements IBaseInfoView {

    @InjectView(R.id.spinner_temporary_count)
    Spinner mSpinnerTemporaryCount;
    @InjectView(R.id.btn_publish)
    Button mBtnPublish;
    private ProgressDialog mProgressDialog;

    protected List<BaseInfo> mList = new ArrayList<>();
    protected BaseInfo guest;

    protected abstract void postInfo();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("加入中...");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // 先加载有数据才有可能click，即先需要回调showInfos
    @Override
    protected void onItemClickEvent(View view, int position) {
        initGuest(position);
        final AlertDialog joinDialog = createJoinDialog();
        mBtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (postStatusNotOk()) {
                    return;
                }
                SharedAnimation.postScaleAnimation(v);
                postInfo(); // 模板方法
                joinDialog.dismiss();
            }
        });
    }

    /**
     * 检查joinDialog所填信息是否无效
     *
     * @return
     */
    private boolean postStatusNotOk() {
        if (!guest.completedAllInfo()) {
            return true;
        }
        return false;
    }


    private AlertDialog createJoinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("填写信息，加入后可查看其他成员信息。");
        View dialogView = View.inflate(getActivity(), R.layout.dialog_join, null);
        ButterKnife.inject(this, dialogView);
        builder.setView(dialogView);
        final AlertDialog joinDialog = builder.create();
        joinDialog.show();

        mSpinnerTemporaryCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guest.setTemporaryCount((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBtnPublish.setText("确认加入");
        return joinDialog;
    }


    private void initGuest(int position) {
        guest = mList.get(position);
        guest.setName(SPUtils.getString(LoginActivity.INFOS[0]));
        guest.setTel(SPUtils.getString(LoginActivity.INFOS[1]));
        guest.setShortTel(SPUtils.getString(LoginActivity.INFOS[2]));
        guest.setWechat(SPUtils.getString(LoginActivity.INFOS[3]));
        guest.setFlag("guest");
        guest.setImei(AppInfoUtils.getImei());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }



    @Override
    public void notRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showInfos(List<? extends BaseInfo> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void noInfos() {
        toast("没有数据...");
    }

    @Override
    public void netError() {
        toast("网络异常，请稍后再试...");
    }

    @Override
    public void showPostProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissPostProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void infoExist() {
        toast("当前已加入，请勿重复操作...");
    }

}
