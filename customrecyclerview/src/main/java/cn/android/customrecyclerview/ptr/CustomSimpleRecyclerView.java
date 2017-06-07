package cn.android.customrecyclerview.ptr;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import cn.android.customrecyclerview.listener.IHeaderView;
import cn.android.customrecyclerview.R;
import cn.android.customrecyclerview.listener.OnViewClick;
import cn.android.customrecyclerview.simple.SimpleImageView;
import cn.android.customrecyclerview.simple.SimpleTextView;


/**
 * Created by yao on 16/6/12.
 */
public class CustomSimpleRecyclerView extends CustomRecyclerView implements OnViewClick,SwipeRefreshLayout.OnRefreshListener{

    private static final String TAG = "WdSimplePtrContainer";
    
    public CustomSimpleRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSimpleRecyclerView(Context context) {
        this(context, null);
    }

    public CustomSimpleRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(!isSwipeToRefresh && mRefreshListener != null){
            setHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.simple_header, CustomSimpleRecyclerView.this, false));
        }
    }

    public CustomSimpleRecyclerView setHeaderStrings(final String[] strArray){
        this.post(new Runnable() {
            @Override
            public void run() {
                for (IHeaderView headerView : mHeaderViews) {
                    if (headerView instanceof SimpleTextView) {
                        ((SimpleTextView) headerView).setPullToRefreshText(strArray[0]);
                        ((SimpleTextView) headerView).setReleaseToRefreshText(strArray[1]);
                        ((SimpleTextView) headerView).setOnRefreshText(strArray[2]);
                    }
                }
            }
        });

        return this;
    }

    public CustomSimpleRecyclerView setHeaderDrawables(final int[] drawable){
        this.post(new Runnable() {
            @Override
            public void run() {
                for (IHeaderView headerView : mHeaderViews) {
                    if (headerView instanceof SimpleImageView) {
                        ((SimpleImageView) headerView).setPullToRefreshDrawable(getContext().getResources().getDrawable(drawable[0]));
                        ((SimpleImageView) headerView).setReleaseToRefreshDrawable(getContext().getResources().getDrawable(drawable[1]));
                    }
                }
            }
        });

        return this;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
