package com.leelit.stuer.receiver;

import android.content.Context;
import android.content.Intent;

import com.leelit.stuer.MyBusinessActivity;
import com.leelit.stuer.constant.MyBusinessConstant;
import com.leelit.stuer.utils.XingeUtils;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by Leelit on 2016/4/23.
 */
public class MyXGResultReceiver extends XGPushBaseReceiver {
    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {

    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {
        Intent intent = new Intent(context, MyBusinessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (xgPushClickedResult.getContent().equals(XingeUtils.CARPOOL_Message)) {
            intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.CARPOOL);
            context.startActivity(intent);
        } else if (xgPushClickedResult.getContent().equals(XingeUtils.DATE_Message)) {
            intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.DATE);
            context.startActivity(intent);
        }
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {

    }
}
