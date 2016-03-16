package com.leelit.stuer.bean;

import android.text.TextUtils;

/**
 * Created by Leelit on 2016/1/6.
 */
public class CarpoolingInfo extends BaseInfo {

    String route;

    @Override
    public String toString() {
        return "CarpoolingInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", shortTel='" + shortTel + '\'' +
                ", wechat='" + wechat + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", temporaryCount='" + temporaryCount + '\'' +
                ", flag='" + flag + '\'' +
                ", imei='" + imei + '\'' +
                ", uniquecode='" + uniquecode + '\'' +
                ", route='" + route + '\'' +
                '}';
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public boolean completedAllInfo() {
        return super.completedAllInfo() &&
                !TextUtils.isEmpty(route);

    }

}
