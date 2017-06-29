package com.yaoyao.android.module.welcome;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yaoyao.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 欢迎页，BaseFragment
 */
public class WelcomeBaseFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.panelParent)
    RelativeLayout panelParent;
    Unbinder unbinder;
    private int background;
    private String text;

    public WelcomeBaseFragment() {
    }

    public static WelcomeBaseFragment newInstance(int background, String text) {
        WelcomeBaseFragment fragment = new WelcomeBaseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, background);
        args.putString(ARG_PARAM2, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            background = getArguments().getInt(ARG_PARAM1);
            text = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome_base, container, false);
        unbinder = ButterKnife.bind(this, view);
        if(!TextUtils.isEmpty(text)){
            textView.setText(text);
        }
        if(background>0){
            panelParent.setBackgroundColor(ContextCompat.getColor(getActivity(), background));
        }
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
