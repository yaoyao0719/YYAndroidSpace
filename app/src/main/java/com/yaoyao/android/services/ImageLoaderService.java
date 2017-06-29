package com.yaoyao.android.services;

import android.widget.ImageView;

import com.android.imagelibary.ImageLoaderUtil;
import com.yaoyao.android.AppApplication;
import com.yaoyao.android.utils.ViewUtils;


/**
 * Created by yaoyao on 2016/12/27.
 */

public class ImageLoaderService {
    /**
     * 加载项目logo
     * @param url
     * @param imageView
     */
    public static void startupLogo( String url, ImageView imageView) {
        ImageLoaderUtil.getInstance().loadRoundImage(AppApplication.getAppContext(), url, imageView, ViewUtils.dp2px(2), 0);
    }
    public static void loadImage( String url, ImageView imageView) {
        ImageLoaderUtil.getInstance().loadImage(AppApplication.getAppContext(), url, imageView, 0);
    }
}
