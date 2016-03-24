package com.leelit.stuer.bean;

/**
 * Created by Leelit on 2016/3/23.
 */
public class TreeholeInfo {
    String datetime;

    String state;

    String picAddress;

    String uniquecode;

    @Override
    public String toString() {
        return "TreeholeInfo{" +
                "datetime='" + datetime + '\'' +
                ", state='" + state + '\'' +
                ", picAddress='" + picAddress + '\'' +
                ", uniquecode='" + uniquecode + '\'' +
                '}';
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPicAddress() {
        return picAddress;
    }

    public void setPicAddress(String picAddress) {
        this.picAddress = picAddress;
    }

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }
}
