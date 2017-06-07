package cn.android.customrecyclerview.listener;

/**
 * Created by yao on 16/6/12.
 */
public interface IFooterView {

//    void onShow();
//
//    void onLoadMore();
    public static int STATE_LOADING = 1;
    public static int STATE_NO_MORE = 2;
    public static int STATE_WAIT_LOAD = 3;
    public static int STATE_ERROR = 4;

    void onLoading();

    void onLoadFinish(String noMoreText);

    void onWaitToLoadMore();

    void onLoadError(String errorMessage);

    int getCurrentState();
}
