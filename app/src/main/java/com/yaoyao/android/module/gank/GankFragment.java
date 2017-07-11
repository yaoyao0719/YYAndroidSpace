package com.yaoyao.android.module.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/7/11
 * @desc  :访问极客开放接口
 */

public class GankFragment extends BaseFragment{

    Unbinder unbinder;
    public GankFragment() {

    }

    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        unbinder = ButterKnife.bind(this, view);
        initWidget();
        return view;
    }

    @Override
    protected void initData() {

    }

    private void initWidget() {
    }


}
