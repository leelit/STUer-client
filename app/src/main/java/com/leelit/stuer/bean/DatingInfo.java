package com.leelit.stuer.bean;

import android.text.TextUtils;

/**
 * Created by Leelit on 2016/1/17.
 */
public class DatingInfo extends BaseInfo {
    String type;
    String description = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CarpoolingInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tel='" + tel + '\'' +
                ", shortTel='" + shortTel + '\'' +
                ", wechat='" + wechat + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", temporaryCount='" + temporaryCount + '\'' +
                ", flag='" + flag + '\'' +
                ", imei='" + imei + '\'' +
                ", uniquecode='" + uniquecode + '\'' +
                '}';
    }

    @Override
    public boolean completedAllInfo() {
        return super.completedAllInfo() &&
                !TextUtils.isEmpty(type);
    }
}
