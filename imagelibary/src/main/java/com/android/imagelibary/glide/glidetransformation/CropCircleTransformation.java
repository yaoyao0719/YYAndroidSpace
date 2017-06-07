package com.android.imagelibary.glide.glidetransformation;

/**
 * Created by yaoyao on 2016/12/27.
 * Glide 圆形图
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class CropCircleTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private Paint mBorderPaint;
    private float mBorderWidth;

    public CropCircleTransformation(Context context) {
        this(Glide.get(context).getBitmapPool());
    }
    public CropCircleTransformation(Context context,int borderWidth,int borderColor) {
        this(Glide.get(context).getBitmapPool());
        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        Log.e("mBorderWidth------aaaaa",""+mBorderWidth);
    }
    public CropCircleTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        if (source == null)
            return null;

        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int width = (source.getWidth() - size) / 2;
        int height = (source.getHeight() - size) / 2;

        Bitmap squared = Bitmap.createBitmap(source, width, height, size, size);
        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
//        mBorderWidth = 10;
//        mBorderPaint = new Paint();
//        mBorderPaint.setDither(true);
//        mBorderPaint.setAntiAlias(true);
//        mBorderPaint.setColor(Color.parseColor("#ff00ff"));
//        mBorderPaint.setStyle(Paint.Style.STROKE);
//        mBorderPaint.setStrokeWidth(mBorderWidth);
        if (mBorderPaint != null) {
            Log.e("mBorderPaint----",""+mBorderWidth);
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }else{
            Log.e("mBorderPaint---null-","null"+mBorderWidth);
        }
        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override public String getId() {
        return getClass().getName();
    }
}
