package com.yaoyao.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.yaoyao.android.model.DaoMaster;


/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/15
 * @desc  :
 */

public class DBHelper extends DaoMaster.OpenHelper{
    public DBHelper(Context context, String name) {
        super(context, name);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //Use db.execSQL(...) to execute SQL for schema updates
        //使用db.execSQL(...)去执行修改、增加、删除表的SQL语句，完成数据库的更新
    }
}
