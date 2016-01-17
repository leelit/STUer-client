package com.leelit.stuer.constant;

import com.leelit.stuer.utils.PhoneInfoUtils;

/**
 * Created by Leelit on 2016/1/6.
 */
public class NetConstant {

    public static final int NET_ERROR_RECORD_EXISTED = 601;


    public static final String HOST = "http://192.168.191.1:8080";

    public static final String STUER = HOST + "/STUer";

    public static final String CARPOOL_BASE = STUER + "/carpool";

    public static final String CARPOOL_CREATE = CARPOOL_BASE + "/create";

    public static final String CARPOOL_QUERY = CARPOOL_BASE + "/query";

    public static final String CARPOOL_EXIT = CARPOOL_BASE + "/delete";

    public static final String DATE_BASE = STUER + "/date";

    public static final String DATE_CREATE = DATE_BASE + "/create";

    public static final String DATE_QUERY = DATE_BASE + "/query";

    public static final String DATE_EXIT = DATE_BASE + "/delete";

    public static String getImeiQueryAddress() {
        return CARPOOL_QUERY + "?imei=" + PhoneInfoUtils.getImei();
    }

}
