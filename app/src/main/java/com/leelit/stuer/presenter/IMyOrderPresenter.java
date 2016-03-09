package com.leelit.stuer.presenter;

import java.util.Map;

/**
 * Created by Leelit on 2016/3/9.
 */
public interface IMyOrderPresenter {
    void doLoadingInfos(String imei);

    void doQuitOrder(Map<String, String> map, int position);

    void doFinishOrder(String uniquecode, int position);
}
