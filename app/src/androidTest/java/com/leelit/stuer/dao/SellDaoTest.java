package com.leelit.stuer.dao;

import android.util.Log;

import com.leelit.stuer.bean.SellInfo;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellDaoTest extends TestCase {

    private static final String[] keys = {"name", "tel", "shorttel", "wechat", "dt", "imei", "picaddress", "state", "flag", "status"};


    public void testSave() throws Exception {
        SellDao sellDao = new SellDao();
        List<SellInfo> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            SellInfo sellInfo = new SellInfo();
            sellInfo.setName(keys[0] + i);
            sellInfo.setTel(keys[1] + i);
            sellInfo.setShortTel(keys[2] + i);
            sellInfo.setWechat(keys[3] + i);
            sellInfo.setDatetime(keys[4] + i);
            sellInfo.setImei(keys[5] + i);
            sellInfo.setPicAddress(keys[6] + i);
            sellInfo.setState(keys[7] + i);
            sellInfo.setUniquecode(keys[8] + i);
            sellInfo.setStatus(keys[9] + i);
            list.add(sellInfo);
        }
        sellDao.save(list);
        Log.e("tag", sellDao.getAll("sell").toString());
    }


    public void testGetLatestDatetime() throws Exception {
        SellDao sellDao= new SellDao();
        String str =sellDao.getLatestDatetime();
        Log.e("tag", str);
    }
}