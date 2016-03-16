package com.leelit.stuer.sell;

import com.leelit.stuer.bean.SellInfo;

import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public interface ISellView {
    void netError();

    void showInfos(List<SellInfo> sellInfos);

    void notRefreshing();
}
