package com.leelit.stuer.base_view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leelit.stuer.MyBusinessActivity;
import com.leelit.stuer.R;
import com.leelit.stuer.base_presenter.IMyBaseInfoPresenter;
import com.leelit.stuer.base_view.viewinterface.IMyBaseInfoView;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.events.DelBaseInfoEvent;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.MyPushUtils;
import com.leelit.stuer.utils.ProgressDialogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leelit on 2016/3/2.
 */
public abstract class MyBaseInfoFragment extends BaseListFragment implements IMyBaseInfoView {

    protected List<List<? extends BaseInfo>> mList = new ArrayList<>();

    private IMyBaseInfoPresenter mPresenter;

    protected abstract IMyBaseInfoPresenter bindPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = bindPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void refreshTask() {
        mPresenter.doLoadingData(AppInfoUtils.getImei());
    }

    @Override
    protected void onItemClickEvent(View view, final int position) {

        // 这里是否应该推到presenter ? 如果推到presenter，MyCarpoolPresenter/MyDatePresenter两个都必须实现这同一个这个方法！

        // 找到自己的info
        List<? extends BaseInfo> relativeInfos = mList.get(position);
        BaseInfo rightInfo = null;
        for (int i = 0; i < relativeInfos.size(); i++) {
            BaseInfo current = relativeInfos.get(i);
            if (current.getImei().equals(AppInfoUtils.getImei())) {
                rightInfo = current;
            }
        }
        // host解散，guest离开
        final BaseInfo finalRightInfo = rightInfo;
        if (rightInfo != null) {
            if (rightInfo.getFlag().equals("host")) {
                new AlertDialog.Builder(getContext()).setMessage("解散当前行列，注意，此举并不会通知你的拼友！").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishThisOrder(finalRightInfo, position); // 解散
                    }
                }).setNegativeButton("取消", null).create().show();
            } else {
                new AlertDialog.Builder(getContext()).setMessage("退出当前行列").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quitThisOrder(finalRightInfo, position); // 退出
                    }
                }).setNegativeButton("取消", null).create().show();
            }
        }
    }


    private void finishThisOrder(BaseInfo rightInfo, int position) {
        mPresenter.doFinishOrder(rightInfo.getUniquecode(), position);
    }

    private void quitThisOrder(BaseInfo rightInfo, int position) {
        Map<String, String> map = new HashMap<>();
        map.put("uniquecode", rightInfo.getUniquecode());
        map.put("id", String.valueOf(rightInfo.getId()));
        mPresenter.doQuitOrder(map, position);
    }


    @Override
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showData(List<List<? extends BaseInfo>> lists) {
        mList.clear();
        mList.addAll(lists);
        mAdapter.notifyDataSetChanged();
        if (MyBusinessActivity.needPush) {
            MyPushUtils.doPush(mList);
            MyBusinessActivity.needPush = false;
        }
    }

    @Override
    public void noData() {
        toast(getResources().getString(R.string.toast_no_data));
    }

    @Override
    public void netError() {
        toast(getResources().getString(R.string.toast_net_error));
    }

    @Override
    public void showDeleteProgressDialog(String message) {
        ProgressDialogUtils.show(getContext(), message);
    }

    @Override
    public void dismissDeleteProgressDialog() {
        ProgressDialogUtils.dismiss();
    }

    @Override
    public void doAfterDeleteOrderSuccessfully(int position) {
        mAdapter.removeData(position);
        EventBus.getDefault().post(new DelBaseInfoEvent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}
