package com.yaoyao.android.module;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static boolean isLaunching = false;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLaunching = true;
        ButterKnife.bind(this);
        initWidget();
    }


    public void initWidget() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLaunching = false;
    }
}
