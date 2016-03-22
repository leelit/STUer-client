package com.leelit.stuer.base_fragments;

import android.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.leelit.stuer.LoginActivity;
import com.leelit.stuer.R;
import com.leelit.stuer.base_fragments.viewinterface.IBaseInfoView;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.ProgressDialogUtils;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.utils.SharedAnimation;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/2.
 */
public abstract class BaseInfoFragment extends BaseListFragment implements IBaseInfoView {

    @InjectView(R.id.spinner_temporary_count)
    Spinner mSpinnerTemporaryCount;
    @InjectView(R.id.btn_publish)
    Button mBtnPublish;

    protected List<BaseInfo> mList = new ArrayList<>();
    protected BaseInfo guest;

    protected abstract void postInfo();


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
    public void stopRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showData(List<? extends BaseInfo> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void noData() {
        toast("当前没有数据...");
    }

    @Override
    public void netError() {
        toast(getString(R.string.toast_net_error));
    }

    @Override
    public void showJoinProgressDialog() {
        ProgressDialogUtils.show(getContext(), "加入中...");
    }

    @Override
    public void dismissJoinProgressDialog() {
        ProgressDialogUtils.dismiss();
    }

    @Override
    public void showAlreadyJoin() {
        toast("当前已加入，请勿重复操作...");
    }

}
