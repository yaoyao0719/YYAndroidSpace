package com.yaoyao.android;

import android.app.Application;
import android.content.Context;

import com.yaoyao.android.utils.ConfigUtil;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.utils.GlideImageLoader;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/5/17
 * @desc  :application
 */

public class AppApplication extends Application {
    private static AppApplication instance;
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();
        ConfigUtil.init(this);
        initGalleryPhoto();
        initFont();
    }

    public static AppApplication getInstance() {
        return instance;
    }
    //返回
    public static Context getAppContext(){
        return context;
    }
    private void initGalleryPhoto() {
        cn.finalteam.galleryfinal.ImageLoader imageLoader = new GlideImageLoader();
        FunctionConfig config = new FunctionConfig.Builder()
                .setCropSquare(true)
                .setEnableCamera(true)
                .setEnablePreview(true)
                .build();
        CoreConfig coreConfig = new CoreConfig.Builder(AppApplication.this, imageLoader, null)
                .setFunctionConfig(config)
                .setNoAnimcation(true)
                .setDebug(true)
                .build();
        GalleryFinal.init(coreConfig);
    }
    private void initFont() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/FZLTH.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}

