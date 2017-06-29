package com.yaoyao.android.base;

import android.os.Bundle;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/26
 * @desc  :
 */

public abstract class MVPBaseActivity<V,P extends BasePresenter<V>> extends BaseActivity{
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter=initPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    public abstract P initPresenter();
}
