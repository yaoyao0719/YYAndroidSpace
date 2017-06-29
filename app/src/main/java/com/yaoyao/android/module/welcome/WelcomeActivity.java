package com.yaoyao.android.module.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jaeger.library.StatusBarUtil;
import com.rd.PageIndicatorView;
import com.rd.animation.AnimationType;
import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.pagerIndicator)
    PageIndicatorView pagerIndicator;


    private WelcomeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        initWidget();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    public void initWidget() {
        adapter = new WelcomeAdapter(getSupportFragmentManager());
        if (viewPager == null) {
            return;
        }
        viewPager.setAdapter(adapter);
        pagerIndicator.setViewPager(viewPager);
        pagerIndicator.setAnimationType(AnimationType.COLOR);
    }


    private class WelcomeAdapter extends FragmentPagerAdapter {

        public WelcomeAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WelcomeBaseFragment.newInstance(R.color.blue_background, "欢迎页1");
                case 1:
                    return WelcomeBaseFragment.newInstance(R.color.orange_background, "欢迎页2");
                case 2:
                    return WelcomeBaseFragment.newInstance(R.color.purple_background, "欢迎页3");
                case 3:
                    return WelcomeFragment.newInstance();
                default:
                    return new WelcomeBaseFragment();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
