package com.android.imagelibary;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by yaoyao on 2016/12/27.
 */

public interface BaseImageLoaderProvider {
    /**
     * 加载原图
     */
    void loadImage(Context context, String url, ImageView imageView);
    /**
     * 加载圆形图
     */
    void loadCircleImage(Context context, String url, ImageView imageView);
    /**
     * 加载圆形图,右边框
     */
    void loadCircleImageWithBorder(Context context, String url, ImageView imageView, int borderWidth, int borderColor);
    /**
     * 加载圆角图
     */
    void loadRoundImage(Context context, String url, ImageView imageView, int radius, int margin);
    /**
     * 加载原图,可设置占位图
     */
    void loadImage(Context context, String url, ImageView imageView, int placeHolder);
}
