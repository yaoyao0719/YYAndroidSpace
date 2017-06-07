package com.yaoyao.android.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yaoyao.android.R;

public abstract class BaseActivity extends AppCompatActivity {

    public Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        if (getStatusFlag()) {
            initMuliteState();
        }
        initWidget();
    }

    protected boolean getStatusFlag() {
        return false;
    }
    /**
     * 获取状态栏的颜色
     *
     * @return 状态栏的颜色
     */
    protected int getStatusBarColor() {
        return R.color.colorPrimary;
    }

    /**
     * 处理4.4 -5.0以及5.0以上沉浸式状态栏
     */
    protected void initMuliteState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上){
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(getResources().getColor(getStatusBarColor()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    public abstract void initWidget();
}
