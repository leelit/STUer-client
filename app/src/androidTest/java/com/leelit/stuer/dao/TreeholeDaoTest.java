package com.leelit.stuer.dao;

import android.util.Log;

import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.TimeUtils;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/25.
 */
public class TreeholeDaoTest extends TestCase {

    public void testSave() throws Exception {
        TreeholeDao treeholeDao = new TreeholeDao();
        List<TreeholeInfo> treeholeInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TreeholeInfo info = new TreeholeInfo();
            info.setState("hehe" + i);
            info.setDatetime(TimeUtils.getCurrentTime());
            info.setUniquecode(AppInfoUtils.getUniqueCode());
            info.setPicAddress("empty");
            treeholeInfos.add(info);
        }
        treeholeDao.save(treeholeInfos);
    }

    public void testGetAll() throws Exception {
        TreeholeDao treeholeDao = new TreeholeDao();
        Log.e("tag", treeholeDao.getAll().toString());
    }
}