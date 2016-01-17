package com.leelit.stuer.bean;

import android.text.TextUtils;

/**
 * Created by Leelit on 2016/1/17.
 */
public class BaseInfo {

    int id;
    String name;
    String tel;
    String shortTel;
    String wechat;
    String date;
    String time;
    String temporaryCount;
    String flag;
    String imei;
    String uniquecode;

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
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShortTel() {
        return shortTel;
    }

    public void setShortTel(String shortTel) {
        this.shortTel = shortTel;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemporaryCount() {
        return temporaryCount;
    }

    public void setTemporaryCount(String temporaryCount) {
        this.temporaryCount = temporaryCount;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }


    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    public boolean completedAllInfo() {
        return (!TextUtils.isEmpty(name) &&
                !TextUtils.isEmpty(tel) &&
                !TextUtils.isEmpty(shortTel) &&
                !TextUtils.isEmpty(wechat) &&
                !TextUtils.isEmpty(temporaryCount) &&
                !TextUtils.isEmpty(date) &&
                !TextUtils.isEmpty(time) &&
                !TextUtils.isEmpty(flag));
    }
}
