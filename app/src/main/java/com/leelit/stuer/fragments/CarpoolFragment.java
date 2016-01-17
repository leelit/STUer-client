package com.leelit.stuer.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.leelit.stuer.MineActivity;
import com.leelit.stuer.R;
import com.leelit.stuer.common.SharedAnimation;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.GsonUtils;
import com.leelit.stuer.utils.OkHttpUtils;
import com.leelit.stuer.utils.PhoneInfoUtils;
import com.leelit.stuer.utils.SPUtils;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2015/12/6.
 */
public class CarpoolFragment extends BaseCarpoolFragment {

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

    private CarpoolingInfo guest;
    private Call mCall;

    private static final String[] keys = {"ET_NAME_GUEST", "ET_TEL_GUEST", "ET_SHORT_TEL_GUEST", "ET_WECHAT_GUEST"};


    public static CarpoolFragment getInstance() {
        CarpoolFragment carpoolFragment = new CarpoolFragment();
        return carpoolFragment;
    }


    @Override
    String getQueryUrl() {
        return NetConstant.CARPOOL_QUERY;
    }

    @Override
    void onItemClickEvent(View view, int position) {
        guest = mList.get(position);
        initGuest();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("填写信息");
        View dialogView = View.inflate(getActivity(), R.layout.dialog_carpool_join, null);
        ButterKnife.inject(this, dialogView);
        initSP();
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
        mBtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedAnimation.postScaleAnimation(v);
                postInfo();
                joinDialog.dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initGuest() {
        guest.setName("");
        guest.setTel("");
        guest.setShortTel("");
        guest.setWechat("");
        guest.setFlag("guest");
        guest.setImei(PhoneInfoUtils.getImei());
    }


    private void postInfo() {
        guest.setName(mEtName.getText().toString());
        guest.setTel(mEtTel.getText().toString());
        guest.setShortTel(mEtShortTel.getText().toString());
        guest.setWechat(mEtWechat.getText().toString());
        if (isEmpty(mEtName) || isEmpty(mEtTel) || isEmpty(mEtShortTel) || isEmpty(mEtWechat)) {
            return;
        }
        if (!guest.completedAllInfo()) {
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("加入中...");
        progressDialog.show();

        mCall = OkHttpUtils.postOnUiThread(NetConstant.CARPOOL_CREATE, GsonUtils.toJson(guest), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                if (!mCall.isCanceled()) {
                    toast("网络出错...");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(final Response response) throws IOException {
                if (response.code() == NetConstant.NET_ERROR_RECORD_EXISTED) {
                    toast("当前已加入，请勿重复操作...");
                    progressDialog.dismiss();
                    return;
                }
                saveSP();
                progressDialog.dismiss();
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        }, getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCall != null) {
            mCall.cancel();
        }
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
            String et_value = SPUtils.get(keys[i]);
            if (!TextUtils.isEmpty(et_value)) {
                editText[i].setText(et_value);
            }
        }
    }

    private void saveSP() {
        String[] values = {guest.getName(), guest.getTel(), guest.getShortTel(), guest.getWechat()};
        SPUtils.save(keys, values);
    }


}
