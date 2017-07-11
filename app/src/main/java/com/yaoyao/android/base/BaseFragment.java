package com.yaoyao.android.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author :yaoyao
 * @email  :229847401@qq.com
 * @date   :2017/7/11
 * @desc   :Fragment基类，
 * @comment:1.如果需要懒加载，则将初始化写到initData；反之，写在initView()中，initData()留空即可；
 *          2.如果是与ViewPager一起使用，调用的是setUserVisibleHint；
 *          3.如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged；
 *          4.针对初始就show的Fragment 为了触发onHiddenChanged事件 达到lazy效果 需要先hide再show（ViewPager不需要）
 */

public abstract class BaseFragment extends Fragment {

    /**
     * 是否可见标志位
     */
    private boolean isVisible;
    /**
     * View是否初始化完成的标志位
     */
    private boolean isPrepared;
    /**
     * 是否第一次加载标志位
     */
    private boolean isFirstLoad=true;

    public BaseFragment(){
        //空构造方法
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFirstLoad=true;
        View view=initView(inflater, container, savedInstanceState);
        isPrepared=true;
        lazyLoad();
        return view;
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible=true;
            onVisible();
        }else {
            isVisible=false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            isVisible=true;
            onVisible();
        }else {
            isVisible=false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    private void onInvisible() {

    }

    protected void lazyLoad(){
        if(!isPrepared||!isVisible||!isFirstLoad){
            return;
        }
        isFirstLoad=false;
        initData();
    }

    protected abstract View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initData();
}
