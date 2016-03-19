package com.leelit.stuer.dao;

/**
 * Created by Leelit on 2016/3/16.
 */
public class Table {

    public static final String SELL_TABLE_CREATE = "create table sell (id integer primary key autoincrement," +
            "name varchar(255)," +
            "tel varchar(255)," +
            "shorttel varchar(255)," +
            "wechat varchar(255)," +
            "dt datetime," +
            "imei varchar(255)," +
            "picaddress varchar(255)," +
            "state varchar(255)," +
            "uniquecode varchar(255)," +
            "status varchar(255)" +
            ")";

    public static final String SELL_COLLECTOR_TABLE_CREATE = "create table sell_collector (id integer primary key autoincrement," +
            "name varchar(255)," +
            "tel varchar(255)," +
            "shorttel varchar(255)," +
            "wechat varchar(255)," +
            "dt datetime," +
            "imei varchar(255)," +
            "picaddress varchar(255)," +
            "state varchar(255)," +
            "uniquecode varchar(255)," +
            "status varchar(255)" +
            ")";
}
