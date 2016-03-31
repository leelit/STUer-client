package com.leelit.stuer.module_baseinfo.carpool.presenter;

import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.base_presenter.IPresenter;
import com.leelit.stuer.base_view.viewinterface.IBaseInfoView;
import com.leelit.stuer.module_baseinfo.carpool.model.CarpoolModel;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/8.
 */
public class CarpoolPresenter implements IPresenter {

    // Model此处无法抽象，因为接口不同；
    // Retrofit使用Gson进行字符串解析，并且RxJava#Observable<T>不能使用通配符，所以Gson从String-Object时必须指定确切类型，如果指定父类，则会丢失信息。
    // 使用Retrofit配合Gson不管是post还是get，解析的类型都是特定的，父类会丢失子类信息。

    private CarpoolModel mModel = new CarpoolModel();

    private IBaseInfoView mView;
    private Subscriber<List<CarpoolingInfo>> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;

    public CarpoolPresenter(IBaseInfoView view) {
        mView = view;
    }

    public void doLoadingData() {
        mSubscriber1 = new Subscriber<List<CarpoolingInfo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (mView != null) {
                    mView.stopRefreshing();
                    mView.netError();
                }
            }

            @Override
            public void onNext(List<CarpoolingInfo> carpoolingInfos) {
                mView.stopRefreshing();
                mView.showData(carpoolingInfos);
                if (carpoolingInfos.isEmpty()) {
                    mView.noData();
                }
            }
        };
        mModel.getGroupRecords(mSubscriber1);
    }


    public void doPostData(final CarpoolingInfo info) {
        mView.showJoinProgressDialog();
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (e.toString().split(" ")[2].equals(String.valueOf(NetConstant.NET_ERROR_RECORD_EXISTED))) {
                    mView.showAlreadyJoin();
                } else {
                    mView.netError();
                }
                mView.dismissJoinProgressDialog();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissJoinProgressDialog();
                mView.doAfterJoinSuccessfully();
            }
        };
        mModel.addRecord(info, mSubscriber2);
    }


    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        if (mSubscriber2 != null) {
            mSubscriber2.unsubscribe();
        }
        mView = null;
    }
}
