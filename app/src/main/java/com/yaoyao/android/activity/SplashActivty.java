package com.yaoyao.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.yaoyao.android.MainActivity;
import com.yaoyao.android.R;
import com.yaoyao.android.activity.welcome.WelcomeActivity;
import com.yaoyao.android.base.BaseActivity;
import com.yaoyao.android.model.Constants;
import com.yaoyao.android.utils.AppUtils;
import com.yaoyao.android.utils.ConfigUtil;
import com.yaoyao.android.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date :2017/6/14
 * @desc :闪屏页
 */

public class SplashActivty extends BaseActivity {

    @BindView(R.id.splashImage)
    ImageView splashImage;
    @BindView(R.id.countDown)
    TextView countDown;
    @BindView(R.id.jumpBtn)
    LinearLayout jumpBtn;
    @BindView(R.id.panelLogo)
    LinearLayout panelLogo;

    /**
     * 广告图的固定比例，在屏幕上显示高度为width*AD_SCALE
     */
    private final static float AD_SCALE = (float) 1476 / 1080;
    private final static int TIME_OUT = 3000;//请求广告超时时间
    private boolean isNewInstall=false;
    private int timeLen=4;
    private Handler handler;
    /**
     * 标记是否进入splashRunnable ，开始倒计时
     */
    private boolean isBeginCountDown=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        MainActivity.isLaunching=false;
        if (MainActivity.isLaunching) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            finish();
            return;
        }
        initSplashImage();

        int lastVersion= ConfigUtil.getIntConfig(Constants.LAST_VERSION,0);
        int currentVersion = AppUtils.getVersionCode();
        if(lastVersion==0||lastVersion!=currentVersion){
            isNewInstall=true;
            ConfigUtil.setConfig(Constants.LAST_VERSION, currentVersion, true);
        }

        if(isNewInstall){
            Intent intent =new Intent(mContext, WelcomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            handler=new Handler();
            handler.postDelayed(splashRunnable,300);
        }
    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTransparent(this);
    }

    private void initSplashImage() {
        int imgHeight=(int)(ViewUtils.getScreenWidth()*AD_SCALE);
        splashImage.setLayoutParams(new android.widget.FrameLayout.LayoutParams(ViewUtils.getScreenWidth(),imgHeight));
        if(ViewUtils.getScreenHeight()-imgHeight>ViewUtils.dp2px(115)){
            android.widget.FrameLayout.LayoutParams params=new android.widget.FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewUtils.getScreenHeight()-imgHeight);
            params.gravity= Gravity.BOTTOM;
            panelLogo.setLayoutParams(params);
            panelLogo.setGravity(Gravity.CENTER);
        }
    }

    private Runnable splashRunnable=new Runnable() {
        @Override
        public void run() {
            if(isBeginCountDown!=false){
                //刷新定时器
                timeLen--;
                if(timeLen<=1){
                    isBeginCountDown=false;
                    if(timeLen==1){
                        countDown.setText(""+timeLen);
                    }
                }else{
                    countDown.setText(""+timeLen);
                }
                handler.postDelayed(this, 1000);
            }else{
                goToMain();
                handler.removeCallbacks(this);
            }
        }
    };

    private void goToMain() {
        Intent intent=new Intent(mContext,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
