package com.yaoyao.android.base;

import android.content.Context;
import android.os.Bundle;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/26
 * @desc  :
 */

public class TestMVPActivity extends MVPBaseActivity{
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context=this;
    }
}
