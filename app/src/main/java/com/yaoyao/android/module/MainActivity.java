package com.yaoyao.android.module;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseActivity;
import com.yaoyao.android.module.gank.GankFragment;
import com.yaoyao.android.module.practice.PracticeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    public static boolean isLaunching = false;
    public static int FRAGMENT_NUM=4;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLaunching = true;
        ButterKnife.bind(this);
        initWidget();
    }


    public void initWidget() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if(tabId==R.id.tab1){
                    viewPager.setCurrentItem(0);
                }
                if(tabId==R.id.tab2){
                    viewPager.setCurrentItem(1);
                }
                if(tabId==R.id.tab3){
                    viewPager.setCurrentItem(2);
                }
                if(tabId==R.id.tab4){
                    viewPager.setCurrentItem(3);
                }
            }
        });
        adapter=new MainAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class MainAdapter extends FragmentPagerAdapter{

        public MainAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return PracticeFragment.newInstance("#ffffff");
                case 1:
                    return GankFragment.newInstance();
                case 2:
                    return PracticeFragment.newInstance("#ff0000");
                case 3:
                    return PracticeFragment.newInstance("#0000ff");
            }
            return null;
        }

        @Override
        public int getCount() {
            return FRAGMENT_NUM;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isLaunching = false;
    }
}
