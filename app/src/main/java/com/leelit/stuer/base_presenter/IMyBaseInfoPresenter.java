package com.leelit.stuer.base_presenter;

import com.leelit.stuer.base_presenter.IPresenter;

import java.util.Map;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface IMyBaseInfoPresenter extends IPresenter {
    void doLoadingData(String imei);

    void doQuitOrder(Map<String, String> map, int position);

    void doFinishOrder(String uniquecode, int position);

}
