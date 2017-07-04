package com.yaoyao.android.glide;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.yaoyao.android.services.FileStoreService;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/5/17
 * @desc  :Glide 配置
 */

public class GlideConfiguration implements GlideModule{

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
