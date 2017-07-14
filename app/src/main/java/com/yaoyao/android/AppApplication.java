package com.yaoyao.android;

import android.app.Application;
import android.content.Context;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;
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
        initOkGo();
        initGalleryPhoto();
        initFont();
    }

    private void initOkGo() {
        HttpParams params =new HttpParams();
        params.put("","");
        OkGo.getInstance().init(this)
                .setCacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST) //设置全局缓存模式
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)     //设置缓存有效期，默认为永不失效
                .setRetryCount(3)                                 //设置超时重连次数，默认为三次
               // .addCommonParams(params)                          //添加全局公共参数
        ;

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

