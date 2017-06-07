package cn.android.customrecyclerview.simple;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import cn.android.customrecyclerview.listener.IHeaderView;


/**
 * Created by yao on 16/6/12.
 */
public class SimpleTextView extends TextView implements IHeaderView {

    private String pullToRefreshText;
    private String releaseToRefreshText;
    private String onRefreshText;

    public void setPullToRefreshText(String pullToRefreshText) {
        this.pullToRefreshText = pullToRefreshText;
    }

    public void setReleaseToRefreshText(String releaseToRefreshText) {
        this.releaseToRefreshText = releaseToRefreshText;
    }

    public void setOnRefreshText(String onRefreshText) {
        this.onRefreshText = onRefreshText;
    }

    public SimpleTextView(Context context) {
        this(context, null);
    }

    public SimpleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void pullToRefresh() {
        if(!TextUtils.isEmpty(pullToRefreshText)){
            setText(pullToRefreshText);
        }
    }

    @Override
    public void releaseToRefresh() {
        if(!TextUtils.isEmpty(releaseToRefreshText)){
            setText(releaseToRefreshText);
        }
    }

    @Override
    public void onRefresh() {
        if(!TextUtils.isEmpty(onRefreshText)){
            setText(onRefreshText);
        }
    }

    @Override
    public void onReset(float distance, float fraction) {

    }

    @Override
    public void onPull(float distance, float fraction) {

    }
}
