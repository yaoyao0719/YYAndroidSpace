package cn.android.customrecyclerview.simple;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.android.customrecyclerview.listener.IFooterView;
import cn.android.customrecyclerview.R;


/**
 * Created by yao on 16/6/12.
 */
public class SimpleFooterView extends FrameLayout implements IFooterView {

    private String onShowText;
    private String onLoadMoreText;
    private Context mContext;
    private View mContentView;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    private int currentState = 0;

    public SimpleFooterView(Context context) {
        this(context, null);
    }

    public SimpleFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
      mContentView = LayoutInflater.from(mContext).inflate(R.layout.default_footer,this,true);
      mProgressBar = (ProgressBar) mContentView.findViewById(R.id.progress);
      mTextView = (TextView) mContentView.findViewById(R.id.message);
        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    }

//    @Override
//    public void onShow() {
//        setText(onShowText);
//    }
//
//    @Override
//    public void onLoadMore() {
//        setText(onLoadMoreText);
//    }

//    public void setOnShowText(String onShowText){
//        this.onShowText = onShowText;
//    }
//
//    public void setOnLoadMoreText(String onLoadMoreText){
//        this.onLoadMoreText = onLoadMoreText;
//    }

    public int getCurrentState(){
        return currentState;
    }

    @Override
    public void onLoading() {
        currentState = STATE_LOADING;
        mProgressBar.setVisibility(VISIBLE);
        mTextView.setVisibility(GONE);
//        mTextView.setText("");
    }

    @Override
    public void onLoadFinish(String noMoreText) {
        currentState = STATE_NO_MORE;
        mProgressBar.setVisibility(GONE);
        mTextView.setVisibility(VISIBLE);
        mTextView.setText(TextUtils.isEmpty(noMoreText) ?"暂无更多" : noMoreText);
    }

    @Override
    public void onWaitToLoadMore() {
        currentState = STATE_WAIT_LOAD;
        mProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void onLoadError(String errorMessage) {
        currentState = STATE_ERROR;
        mProgressBar.setVisibility(GONE);
        mTextView.setVisibility(VISIBLE);
        mTextView.setText("加载失败,点击重试");
    }
}
