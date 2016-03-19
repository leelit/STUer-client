package com.leelit.stuer.presenter;

import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.model.CarpoolModel;
import com.leelit.stuer.viewinterface.IMyBaseInfoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by Leelit on 2016/3/9.
 */
public class MyCarpoolPresenter implements IMyBaseInfoPresenter,IPresenter {

    // Model此处无法抽象，因为接口不同；
    // Retrofit使用Gson进行字符串解析，并且RxJava#Observable<T>不能使用通配符，所以Gson从String-Object时必须指定确切类型，如果指定父类，则会丢失信息。
    // 使用Retrofit配合Gson不管是post还是get，解析的类型都是特定的，父类会丢失子类信息。

    private CarpoolModel mModel = new CarpoolModel();

    private IMyBaseInfoView mView;
    private Subscriber<List<List<CarpoolingInfo>>> mSubscriber1;
    private Subscriber<ResponseBody> mSubscriber2;
    private Subscriber<ResponseBody> mSubscriber3;

    public MyCarpoolPresenter(IMyBaseInfoView view) {
        mView = view;
    }

    @Override
    public void doLoadingData(String imei) {
        mSubscriber1 = new Subscriber<List<List<CarpoolingInfo>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.stopRefreshing();
                mView.netError();
            }

            @Override
            public void onNext(List<List<CarpoolingInfo>> lists) {
                mView.stopRefreshing();
                List<List<? extends BaseInfo>> result = new ArrayList<>();
                for (int i = 0; i < lists.size(); i++) {
                    result.add(lists.get(i));
                }
                mView.showData(result);
                if (lists.isEmpty()) {
                    mView.noData();
                }
            }
        };
        mModel.getPersonalRelativeRecords(imei, mSubscriber1);
    }

    @Override
    public void doQuitOrder(Map<String, String> map, final int position) {
        mView.showDeleteProgressDialog("退出中...");
        mSubscriber2 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDeleteProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissDeleteProgressDialog();
                mView.deleteOrder(position);
            }
        };
        mModel.quitOrder(map, mSubscriber2);
    }

    @Override
    public void doFinishOrder(String uniquecode, final int position) {
        mView.showDeleteProgressDialog("解散中...");
        mSubscriber3 = new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.dismissDeleteProgressDialog();
                mView.netError();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                mView.dismissDeleteProgressDialog();
                mView.deleteOrder(position);
            }
        };
        mModel.finishOrder(uniquecode, mSubscriber3);
    }

    @Override
    public void doClear() {
        if (mSubscriber1 != null) {
            mSubscriber1.unsubscribe();
        }
        if (mSubscriber2 != null) {
            mSubscriber2.unsubscribe();
        }
        if (mSubscriber3 != null) {
            mSubscriber3.unsubscribe();
        }
        mView = null;
    }
}
