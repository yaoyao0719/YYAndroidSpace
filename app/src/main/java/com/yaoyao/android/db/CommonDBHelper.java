package com.yaoyao.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yaoyao.android.model.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/15
 * @desc  :
 */

public class CommonDBHelper extends DaoMaster.OpenHelper{

    public CommonDBHelper(Context context, String name) {
        super(context, name);
    }

    public CommonDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onCreate(Database db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }

}
