package com.leelit.stuer.utils;

import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by Leelit on 2016/3/20.
 */
public class SettingUtilsTest extends TestCase {

    public void testGetWantOfflineSell() throws Exception {
        Log.e("tag", String.valueOf(SettingUtils.noOfflineSell()));
    }
}