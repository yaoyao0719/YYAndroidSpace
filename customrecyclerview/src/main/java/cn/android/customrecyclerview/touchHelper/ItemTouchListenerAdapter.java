package cn.android.customrecyclerview.touchHelper;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import cn.android.customrecyclerview.adapter.CustomRecyclerAdapter;
import cn.android.customrecyclerview.listener.OnViewClick;


/**
 * Created by yao on 16/6/12.
 */
public class ItemTouchListenerAdapter extends GestureDetector.SimpleOnGestureListener implements RecyclerView.OnItemTouchListener {

    private OnViewClick listener;
    private RecyclerView recyclerView;
    private GestureDetector gestureDetector;

    public ItemTouchListenerAdapter(RecyclerView recyclerView, OnViewClick listener) {
        if (recyclerView == null || listener == null) {
            throw new IllegalArgumentException("RecyclerView and Listener arguments can not be null");
        }
        this.recyclerView = recyclerView;
        this.listener = listener;
        this.gestureDetector = new GestureDetector(recyclerView.getContext(), this);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
    @Override
    public void onShowPress(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view != null) {
            view.setPressed(true);
        }
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view == null) return false;

        view.setPressed(false);

        int position = recyclerView.getChildAdapterPosition(view);

        RecyclerView.ViewHolder holder = getViewHolderUnder(position);

        RecyclerView.Adapter mAdapter = recyclerView.getAdapter();
        if(mAdapter instanceof CustomRecyclerAdapter){
            position -= ((CustomRecyclerAdapter) mAdapter).getHeaderViewCount();
        }

        if(position < 0){
            return false;
        }

        listener.onClick(holder, position);
        return true;
    }

    public void onLongPress(MotionEvent e) {
        View view = getChildViewUnder(e);
        if (view == null) return;

        int position = recyclerView.getChildAdapterPosition(view);

        RecyclerView.Adapter mAdapter = recyclerView.getAdapter();
        if(mAdapter instanceof CustomRecyclerAdapter){
            position -= ((CustomRecyclerAdapter) mAdapter).getHeaderViewCount();
        }

        RecyclerView.ViewHolder holder = getViewHolderUnder(position);

        if(position < 0){
            return;
        }

        listener.onLongClick(holder, position);
        view.setPressed(false);
    }

    @Nullable
    private View getChildViewUnder(MotionEvent e) {
        return recyclerView.findChildViewUnder(e.getX(), e.getY());
    }

    private RecyclerView.ViewHolder getViewHolderUnder(int position){
        return recyclerView.findViewHolderForAdapterPosition(position);
    }
}

