package com.yaoyao.android.utils;

import android.content.Context;
import android.text.TextUtils;

import com.yaoyao.android.BaseApplication;
import com.yaoyao.android.db.CommonDBManager;
import com.yaoyao.android.model.ConfigInfo;
import com.yaoyao.android.model.ConfigInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/15
 * @desc  :
 */

public class ConfigUtil {
    private static final String TAG = "ConfigUtil";
    private static Map<String, Object> configMap=new HashMap<>();
    private static Map<String, Object> debugConfigMap;
    /**
     * 是否强制升级到服务器版本。用于升级测试。
     */
    public static final String CONFIG_FORCE_UPGRADE = "forceUpgrade";
    public static final String CONFIG_ACCESS_TOEKN = "access_token";
    public static final String CONFIG_DEBUG = "debug";
    public static Context appContext = null;

    public static void init(Context context) {

        appContext = context.getApplicationContext();
        configMap = new HashMap<String, Object>();
        configMap.put(ConfigUtil.CONFIG_DEBUG, false);

        LoadAllConfig();
    }
    public static boolean isInit() {
        if (configMap == null) {
            return false;
        }
        return true;
    }
    public static int getIntConfig(String cfgName, int defVal) {
        Object objInt = getConfig(BaseApplication.getAppContext(),cfgName);
        if (objInt == null) {
            return defVal;
        } else {
            if (objInt instanceof Integer) {
                return (Integer) objInt;
            } else if (objInt instanceof String && TextUtils.isDigitsOnly((CharSequence) objInt)) {
                return Integer.valueOf((String) objInt);
            }
            return defVal;
        }
    }
    public static boolean getBooleanConfig(String cfgName, boolean defVal) {
        Object objBoolean = getConfig(BaseApplication.getAppContext(),cfgName);
        if (objBoolean == null) {
            return defVal;
        } else {
            if (objBoolean instanceof Boolean) {
                return (Boolean) objBoolean;
            } else if (objBoolean instanceof String) {
                String strBoolean = (String) objBoolean;
                if ("true".equals(strBoolean.toLowerCase().trim())) {
                    return true;
                } else if ("false".equals(strBoolean.toLowerCase().trim())) {
                    return false;
                }
            }
            return defVal;
        }
    }

    public static long getLongConfig(String cfgName, Long defVal) {
        Object objLong = getConfig(BaseApplication.getAppContext(),cfgName);
        if (objLong == null) {
            return defVal;
        } else {
            if (objLong instanceof Long) {
                return (Long) objLong;
            } else if (objLong instanceof String && TextUtils.isDigitsOnly((CharSequence) objLong)) {
                return Long.valueOf((String) objLong);
            }
            return defVal;
        }
    }


    public static String getStringConfig(String cfgName, String defVal) {
        Object objString = getConfig(BaseApplication.getAppContext(),cfgName);
        if (objString == null) {
            return defVal;
        } else {
            return objString.toString();
        }
    }

    public static void setConfig(String cfgName, Object cfgVal, boolean isPersistence) {
        if (cfgName == null || cfgName.length() == 0) {
            return;
        }
        configMap.put(cfgName, cfgVal);

        if (!isPersistence) {
            return;
        }

        try {
            ConfigInfo config = new ConfigInfo(cfgName, String.valueOf(cfgVal));
            CommonDBManager.getDaoSession(BaseApplication.getAppContext()).getConfigInfoDao().insertOrReplace(config);
         //   CommonDBManager.getDaoSession(context).getEvcConfigDao().rx().save(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 已temp_开头的配置，表示运行时参数，不在数据库中尝试读取。
     *
     * @param cfgName
     * @return
     */
    public static Object getConfig(Context context,String cfgName) {
        Object ret = null;
        if (!isInit()) {
            return null;
        }
        ret = configMap.get(cfgName);
        //尝试从数据库中获取。
        if (ret == null && cfgName != null && !cfgName.startsWith("temp_")) {

            try {
                QueryBuilder<ConfigInfo> queryBuilder=CommonDBManager.getDaoSession(context).getConfigInfoDao().queryBuilder();
                queryBuilder.where(ConfigInfoDao.Properties.Key.eq(cfgName));
                List<ConfigInfo> list=queryBuilder.list();
                ConfigInfo config ;
                if (list!=null&&list.size()>0&&list.get(0)!= null) {
                    config=list.get(0);
                    ret = config.getValue();
                }
                configMap.put(cfgName, ret);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ret;
    }
    public static boolean removeConfig(String cfgName) {
        if (cfgName == null || cfgName.length() == 0) {
            return true;
        }
        boolean ret = false;
        if (configMap.containsKey(cfgName)) {
            configMap.remove(cfgName);
            ret = true;
        }

        try {
            ConfigInfoDao configDao=CommonDBManager.getDaoSession(BaseApplication.getAppContext()).getConfigInfoDao();
            configDao.deleteByKey(cfgName);
            ret =true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
    public static void LoadAllConfig() {
        if (!isInit()) {
        }
        try {
            ConfigInfoDao configDao=CommonDBManager.getDaoSession(appContext).getConfigInfoDao();
            List<ConfigInfo> list=configDao.loadAll();
            for (ConfigInfo config : list) {
                configMap.put(config.getKey(), config.getValue());
            }
            android.util.Log.i("config map size", "" + configMap.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
