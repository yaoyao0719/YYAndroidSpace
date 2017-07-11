package com.yaoyao.android.module.practice;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date :2017/7/11
 * @desc :
 */

public class PracticeFragment extends BaseFragment {

    private static String BACKGROUND_COLOR = "background_color";

    @BindView(R.id.textView1)
    TextView textView1;
    @BindView(R.id.panelParent)
    LinearLayout panelParent;
    Unbinder unbinder;
    private String backgroundColor;

    public PracticeFragment() {

    }

    public static PracticeFragment newInstance(String color) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle args = new Bundle();
        args.putString(BACKGROUND_COLOR, color);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_practice, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (getArguments() != null) {
            backgroundColor = getArguments().getString(BACKGROUND_COLOR);
            panelParent.setBackgroundColor(Color.parseColor(backgroundColor));
        }
        initWidget();
        return view;
    }

    @Override
    protected void initData() {

    }

    private void initWidget() {
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
