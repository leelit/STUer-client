package com.leelit.stuer.presenter;

import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.constant.SpConstant;
import com.leelit.stuer.model.DateModel;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.viewinterface.IBaseInfoView;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/8.
 */
public class BaseInfoDatePresenter {

    private DateModel mModel = new DateModel();

    private IBaseInfoView mView;

    public BaseInfoDatePresenter(IBaseInfoView view) {
        mView = view;
    }

    public void doLoadingInfos(String type) {
        mModel.getGroupRecords(type, new Subscriber<List<DatingInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.notRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<DatingInfo> datingInfos) {
                mView.notRefreshing();
                mView.showInfos(datingInfos);
                if (datingInfos.isEmpty()) {
                    mView.noInfos();
                }
            }
        });
    }


    public void doPostInfo(final DatingInfo info) {
        mView.showPostProgressDialog();
        mModel.addRecord(info, new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e.toString().split(" ")[2].equals(String.valueOf(NetConstant.NET_ERROR_RECORD_EXISTED))) {
                    mView.infoExist();
                } else {
                    mView.netError();
                }
                mView.dismissPostProgressDialog();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                saveGuestSP(info);
                mView.dismissPostProgressDialog();
                mView.afterPostInfo();
            }
        });
    }

    private void saveGuestSP(BaseInfo guest) {
        String[] values = {guest.getName(), guest.getTel(), guest.getShortTel(), guest.getWechat()};
        SPUtils.save(SpConstant.GUEST_KEYS, values);
    }
}
