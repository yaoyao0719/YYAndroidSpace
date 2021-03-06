package com.yaoyao.android.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.yaoyao.android.model.DaoMaster;
import com.yaoyao.android.model.DaoSession;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/15
 * @desc  :
 */

public class CommonDBManager {

    private static CommonDBManager mInstance;
    private static CommonDBHelper mOpenHelper;
    private static DaoSession mDaoSession;
    private static DaoMaster mDaoMaster;
    private Context mContext;

    public static String getDBName(){
        return "common.db";
    }
    public CommonDBManager(Context context) {
        this.mContext = context;
        mOpenHelper = new CommonDBHelper(mContext, getDBName(), null);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static CommonDBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (CommonDBManager.class) {
                if (mInstance == null) {
                    mInstance = new CommonDBManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可读数据库
     *
     * @return
     */
    private static SQLiteDatabase getReadableDatabase(Context context) {
        if (mOpenHelper == null) {
            mOpenHelper = new CommonDBHelper(context, getDBName(), null);
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     *
     * @return
     */
    public static SQLiteDatabase getWriteableDatabase(Context context) {
        if (mOpenHelper == null) {
            mOpenHelper = new CommonDBHelper(context, getDBName(), null);
        }
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db;
    }


    /**
     * @desc 获取DaoMaster
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (CommonDBManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWriteableDatabase(context));
                }
            }
        }
        return mDaoMaster;
    }

    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (CommonDBManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }
        return mDaoSession;
    }
}
