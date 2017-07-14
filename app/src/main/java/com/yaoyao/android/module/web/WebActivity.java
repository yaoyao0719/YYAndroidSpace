package com.yaoyao.android.module.web;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/7/14
 * @desc  :
 */

public class WebActivity extends BaseActivity {

    public final static String WEB_URL = "web_url";
    public final static String WEB_TITLE = "web_title";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

    private String url;

    public static void runActivity(Context mContext, String title, String url) {
        Intent intent=new Intent(mContext,WebActivity.class);
        intent.putExtra(WEB_URL,url);
        intent.putExtra(WEB_TITLE,title);
        mContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra(WEB_URL);
        String title = getIntent().getStringExtra(WEB_TITLE);
        toolbar.setTitle(title);
        initWidgets();
    }

    private void initWidgets() {
        progressBar.setMax(100);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if(newProgress>=100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
}
