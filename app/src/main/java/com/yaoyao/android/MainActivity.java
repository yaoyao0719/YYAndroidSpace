package com.yaoyao.android;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.yaoyao.android.base.BaseActivity;
import com.yaoyao.android.services.ImageLoaderService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ImageLoaderService.loadImage("http://4493bz.1985t.com/uploads/allimg/150127/4-15012G52133.jpg", image);

    }

    @Override
    protected boolean getStatusFlag() {
        return false;
    }

    @Override
    public void initWidget() {

    }
}
