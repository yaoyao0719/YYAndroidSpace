package cn.android.customrecyclerview.listener;

/**
 * Created by yao on 16/6/12.
 */
public interface ItemTouchAdapter {

    void onMove(int fromPosition, int toPosition);

    void onDismiss(int position);
}
