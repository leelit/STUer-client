package com.leelit.stuer.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leelit.stuer.bean.TreeholeLocalInfo;
import com.leelit.stuer.bean.TreeholeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leelit on 2016/3/25.
 */
public class TreeholeDao {

    private static final String[] keys = {"dt", "state", "picaddress", "uniquecode", "like", "unlike"};

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static void save(List<TreeholeInfo> infos) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        db.beginTransaction();
        for (TreeholeInfo info : infos) {
            ContentValues values = new ContentValues();
            values.put(keys[0], info.getDatetime());
            values.put(keys[1], info.getState());
            values.put(keys[2], info.getPicAddress());
            values.put(keys[3], info.getUniquecode());
            values.put(keys[4], FALSE);
            values.put(keys[5], FALSE);
            db.insert("treehole", null, values);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public static String getLatestDatetime() {
        String str = "";
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM treehole ORDER BY id DESC LIMIT 1", null);
        if (cursor.moveToFirst()) {
            str = cursor.getString(cursor.getColumnIndex("dt"));
        }
        cursor.close();
        return str;
    }

    public static List<TreeholeLocalInfo> getAll() {
        List<TreeholeLocalInfo> result = new ArrayList<>();
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        db.beginTransaction();
        Cursor cursor = db.query("treehole", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                TreeholeLocalInfo comment = new TreeholeLocalInfo();
                comment.setDatetime(cursor.getString(cursor.getColumnIndex(keys[0])));
                comment.setState(cursor.getString(cursor.getColumnIndex(keys[1])));
                comment.setPicAddress(cursor.getString(cursor.getColumnIndex(keys[2])));
                comment.setUniquecode(cursor.getString(cursor.getColumnIndex(keys[3])));
                comment.setLike(TRUE == cursor.getInt(cursor.getColumnIndex(keys[4])));
                comment.setUnlike(TRUE == cursor.getInt(cursor.getColumnIndex(keys[5])));
                result.add(comment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.setTransactionSuccessful();
        db.endTransaction();
        return result;
    }

    public static TreeholeLocalInfo getComment(String uniquecode) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getReadableDatabase();
        Cursor cursor = db.query("treehole", null, "uniquecode = ?", new String[]{uniquecode}, null, null, null);
        TreeholeLocalInfo comment = new TreeholeLocalInfo();
        cursor.moveToFirst();
        comment.setDatetime(cursor.getString(cursor.getColumnIndex(keys[0])));
        comment.setState(cursor.getString(cursor.getColumnIndex(keys[1])));
        comment.setPicAddress(cursor.getString(cursor.getColumnIndex(keys[2])));
        comment.setUniquecode(cursor.getString(cursor.getColumnIndex(keys[3])));
        comment.setLike(TRUE == cursor.getInt(cursor.getColumnIndex(keys[4])));
        comment.setUnlike(TRUE == cursor.getInt(cursor.getColumnIndex(keys[5])));
        cursor.close();
        return comment;
    }

    public static void updateLikeOfComment(String uniquecode, int isLike) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(keys[4], isLike);
        db.update("treehole", values, "uniquecode = ?", new String[]{uniquecode});
    }

    public static void updateUnlikeOfComment(String uniquecode, int isUnlike) {
        SQLiteDatabase db = DatabaseHelper.getInstance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(keys[5], isUnlike);
        db.update("treehole", values, "uniquecode = ?", new String[]{uniquecode});
    }
}
