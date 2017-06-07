package com.android.imagelibary.glide;

import android.content.Context;
import android.widget.ImageView;

import com.android.imagelibary.BaseImageLoaderProvider;
import com.android.imagelibary.glide.glidetransformation.CropCircleTransformation;
import com.android.imagelibary.glide.glidetransformation.GlideCircleTransform;
import com.android.imagelibary.glide.glidetransformation.RoundedCornersTransformation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.yaoyao.imagelibary.R;


/**
 * Created by yaoyao on 2016/12/27.
 */

public class GlideImageLoaderProvider implements BaseImageLoaderProvider {

    /**
     * 占位图
     */
    public static final int PLACE_HOLDER= R.drawable.ic_launcher;

    @Override
    public void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .into(imageView);
    }

    @Override
    public void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }
    @Override
    public void loadCircleImageWithBorder(Context context, String url, ImageView imageView,int borderWidth,int borderColor) {
        Glide.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .bitmapTransform(new GlideCircleTransform(context, borderWidth, borderColor))
                .into(imageView);
    }

    @Override
    public void loadRoundImage(Context context, String url, ImageView imageView, int radius, int margin) {
        Glide.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .bitmapTransform(new RoundedCornersTransformation(context,radius,margin))
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, String url, ImageView imageView, int placeHolder) {
        Glide.with(context)
                .load(url)
                .placeholder(placeHolder)
                .into(imageView);
    }

    public void loadImageWithListener(Context context, String url, ImageView imageView,RequestListener<String,GlideDrawable> requestListener) {
        Glide.with(context)
                .load(url)
                .placeholder(PLACE_HOLDER)
                .listener(requestListener)
                .into(imageView);
    }

}
