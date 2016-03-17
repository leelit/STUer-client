package com.leelit.stuer.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.leelit.stuer.MyApplication;

/**
 * Created by Leelit on 2016/3/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "stuer.db";
    private static final int VERSION = 1;

    private static final DatabaseHelper mDatabaseHelper = new DatabaseHelper(MyApplication.context, DB_NAME, null, VERSION);

    public static DatabaseHelper getInstance() {
        return mDatabaseHelper;
    }

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table.SELL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
