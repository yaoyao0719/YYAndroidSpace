package com.yaoyao.android.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.yaoyao.android.services.FileStoreService;

/**
 * Created by yaoyao on 2016/12/27.
 */

public class MyGlideModule implements GlideModule{

    @Override
    public void applyOptions(Context context, GlideBuilder glideBuilder) {
        /**
         * 设置外部存储缓存路径
         */
        int cacheSize100MegaBytes = 104857600;//最大100M
        glideBuilder.setDiskCache(new DiskLruCacheFactory(FileStoreService.getCacheDirExternal(),cacheSize100MegaBytes));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}
