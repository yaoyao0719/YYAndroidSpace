package com.yaoyao.android.base;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/26
 * @desc  :MVP中V的base类，抽象出大部分view会用的方法
 */

public interface BaseView {

    /**
     * 显示正在加载view
     */
    void showLoading();

    /**
     * 隐藏正在加载view
     */
    void hideLoading();

    /**
     * 显示错误view
     */
    void showError();

    /**
     * 隐藏错误View
     */
    void hideError();

    /**
     * 显示空view
     */
    void showEmpty();

    /**
     * 隐藏空view
     */
    void hideEmpty();
}
