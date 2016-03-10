package com.leelit.stuer.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.leelit.stuer.R;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.common.SharedAnimation;
import com.leelit.stuer.constant.SpConstant;
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

    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.et_tel)
    EditText mEtTel;
    @InjectView(R.id.et_shortTel)
    EditText mEtShortTel;
    @InjectView(R.id.et_wechat)
    EditText mEtWechat;
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

    // 先加载有数据才有可能click，即先需要回调#showInfos
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
        guest.setName(mEtName.getText().toString());
        guest.setTel(mEtTel.getText().toString());
        guest.setShortTel(mEtShortTel.getText().toString());
        guest.setWechat(mEtWechat.getText().toString());
        if (isEmpty(mEtName) || isEmpty(mEtTel) || isEmpty(mEtShortTel) || isEmpty(mEtWechat)) {
            return true;
        }
        if (!guest.completedAllInfo()) {
            return true;
        }
        return false;
    }


    private AlertDialog createJoinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("填写信息，加入后可查看其他成员信息...");
        View dialogView = View.inflate(getActivity(), R.layout.dialog_join, null);
        ButterKnife.inject(this, dialogView);
        initSP(); // Dialog里的EditText先尝试从SharePreference加载
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
        guest.setName("");
        guest.setTel("");
        guest.setShortTel("");
        guest.setWechat("");
        guest.setFlag("guest");
        guest.setImei(AppInfoUtils.getImei());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private boolean isEmpty(EditText et) {
        if (TextUtils.isEmpty(et.getText())) {
            et.setError("不能为空");
            return true;
        }
        return false;
    }

    private void initSP() {
        EditText[] editText = {mEtName, mEtTel, mEtShortTel, mEtWechat};
        for (int i = 0; i < 4; i++) {
            String et_value = SPUtils.get(SpConstant.GUEST_KEYS[i]);
            if (!TextUtils.isEmpty(et_value)) {
                editText[i].setText(et_value);
            }
        }
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
