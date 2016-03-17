package com.leelit.stuer.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leelit.stuer.bean.SellInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/16.
 */
public class SellDao {

    public void save(List<SellInfo> infos) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        for (SellInfo info : infos) {
            ContentValues values = new ContentValues();
            values.put(keys[0], info.getName());
            values.put(keys[1], info.getTel());
            values.put(keys[2], info.getShortTel());
            values.put(keys[3], info.getWechat());
            values.put(keys[4], info.getDatetime());
            values.put(keys[5], info.getImei());
            values.put(keys[6], info.getPicAddress());
            values.put(keys[7], info.getState());
            values.put(keys[8], info.getFlag());
            values.put(keys[9], info.getStatus());
            db.insert("sell", null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private static final String[] keys = {"name", "tel", "shorttel", "wechat", "dt", "imei", "picaddress", "state", "flag", "status"};


    public List<SellInfo> getAll() {
        List<SellInfo> result = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query("sell", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                SellInfo sellInfo = new SellInfo();
                sellInfo.setName(cursor.getString(cursor.getColumnIndex(keys[0])));
                sellInfo.setTel(cursor.getString(cursor.getColumnIndex(keys[1])));
                sellInfo.setShortTel(cursor.getString(cursor.getColumnIndex(keys[2])));
                sellInfo.setWechat(cursor.getString(cursor.getColumnIndex(keys[3])));
                sellInfo.setDatetime(cursor.getString(cursor.getColumnIndex(keys[4])));
                sellInfo.setImei(cursor.getString(cursor.getColumnIndex(keys[5])));
                sellInfo.setPicAddress(cursor.getString(cursor.getColumnIndex(keys[6])));
                sellInfo.setState(cursor.getString(cursor.getColumnIndex(keys[7])));
                sellInfo.setFlag(cursor.getString(cursor.getColumnIndex(keys[8])));
                sellInfo.setStatus(cursor.getString(cursor.getColumnIndex(keys[9])));
                result.add(sellInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

}
