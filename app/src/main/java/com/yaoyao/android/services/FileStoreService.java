package com.yaoyao.android.services;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.yaoyao.android.BaseApplication;
import com.yaoyao.android.utils.FileUtils;

import java.io.File;

/**
 * Created by lane on 2014/12/29.
 */
public class FileStoreService {

    /**
     * 当前应用的sd根目录
     *
     * @return
     */
    public static String getSDRootDir(){
        return Environment.getExternalStorageDirectory()+"/kaipai";
    }
    /**
     * 当前用户的目录。
     * 为了支持多用户切换，不同用户的数据，存储在不同的目录下。
     * @return
     */
    public static String getUserDir(){
       // return getSDRootDir()+"/"+ AccountService.getInstance().userId;
        return null;
    }

    /**
     * 优先使用外置存储作为缓存目录。
     * @return
     */
    public static String getCacheDir(){
        if ( Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            return getSDRootDir()+"/cache";
        } else {
            return BaseApplication.getInstance().getCacheDir()+"/cache";
        }
    }

    /**
     * 优先获取sd卡 Android/data/com.evervc.ttt cache目录
     *
     * @return
     */
    public static String getCacheDirExternal(){
        File externalCacheDir = BaseApplication.getInstance().getExternalCacheDir();
        if ( Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && externalCacheDir!=null && externalCacheDir.exists()){
            return externalCacheDir.getAbsolutePath();
        }else {
            return BaseApplication.getInstance().getCacheDir()+"/cache";
        }
    }

    /**
     * IM相关的目录
     * @param childDir 子目录。 图片、语音、缓存文件等。
     * @return
     */
    public static String getIMDir(String childDir){
        String path = null;
        if ( Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            path = getUserDir()+"/im/"+childDir;
        } else {
            path = BaseApplication.getInstance().getCacheDir().getAbsolutePath()+"/im/"+childDir;
        }

        File dir = new File(path);
        if (!dir.exists()){
            dir.mkdirs();
        }

        return path;
    }


    /**
     * 发送消息的临时缓存目录。
     * @return
     */
    public static String getIMCacheDir(){
        return getIMDir("cache");
    }

    /*public static String getSaveImgDir(){
       return getSDRootDir()+"image";
    }*/

    public static boolean saveImage(File from){
       String rootDir = getSDRootDir();
        if(TextUtils.isEmpty(rootDir)){
            return false;
        }
        String targetDir = rootDir+"/image";
        File imageDir = new File(targetDir);
        if(!imageDir.exists()){
            imageDir.mkdirs();
        }
        String targetImgPath = targetDir+"/"+ System.currentTimeMillis()+".jpg";
        try {
            FileUtils.copyFile(from.getAbsolutePath(),targetImgPath);
            BaseApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(targetImgPath))));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

}
