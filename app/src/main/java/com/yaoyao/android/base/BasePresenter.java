package com.yaoyao.android.base;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/26
 * @desc  :MVP中P的base
 */

public abstract class BasePresenter<T> {
    public T view;

    public void attach(T view){
        this.view=view;
    }

    public void detach(){
        this.view=null;
    }
}
