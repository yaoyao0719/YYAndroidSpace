package cn.android.customrecyclerview.touchHelper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import cn.android.customrecyclerview.adapter.CustomRecyclerAdapter;
import cn.android.customrecyclerview.listener.ItemTouchAdapter;
import cn.android.customrecyclerview.ptr.CustomRecyclerView;


/**
 * Created by yao on 16/6/12.
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final String TAG = "Callback";

    private final ItemTouchAdapter mAdapter;

    private boolean shouldDrag = false;

    private boolean shouldSwipe = false;

    public SimpleItemTouchHelperCallback(ItemTouchAdapter adapter,boolean shouldDrag,boolean shouldSwipe) {
        mAdapter = adapter;
        this.shouldDrag = shouldDrag;
        this.shouldSwipe = shouldSwipe;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return shouldDrag;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return shouldSwipe;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                          RecyclerView.ViewHolder target) {
        //存在头部或底部，并且点击头部或底部
        if((((CustomRecyclerAdapter) mAdapter).hasHeaderView() && viewHolder.getAdapterPosition() == 0) || (((CustomRecyclerAdapter) mAdapter).hasFootView() && viewHolder.getAdapterPosition() == ((CustomRecyclerAdapter)mAdapter).getItemCount() - 1)){
            return false;
        }

        //存在头部或底部，并且点击头部或底部
        if((((CustomRecyclerAdapter) mAdapter).hasHeaderView() && target.getAdapterPosition() == 0) || (((CustomRecyclerAdapter) mAdapter).hasFootView() && target.getAdapterPosition() == ((CustomRecyclerAdapter)mAdapter).getItemCount() - 1)){
            return false;
        }

        if(((CustomRecyclerAdapter) mAdapter).getPlugAdapter().getItemViewType(viewHolder.getAdapterPosition() - ((CustomRecyclerAdapter) mAdapter).getHeaderViewCount()) == CustomRecyclerView.TYPE_RECYCLER_FOOTER){
            return false;
        }

        mAdapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if(!(mAdapter instanceof CustomRecyclerAdapter)){
            return;
        }

        //存在头部，并且点击头部
        if(((CustomRecyclerAdapter) mAdapter).hasHeaderView() && viewHolder.getAdapterPosition() == 0){
            return;
        }

        //存在底部，并且点击底部
        if(((CustomRecyclerAdapter) mAdapter).hasFootView() && viewHolder.getAdapterPosition() == ((CustomRecyclerAdapter)mAdapter).getItemCount() - 1){
            return;
        }

        if(((CustomRecyclerAdapter) mAdapter).getPlugAdapter().getItemViewType(viewHolder.getAdapterPosition() - ((CustomRecyclerAdapter) mAdapter).getHeaderViewCount()) == CustomRecyclerView.TYPE_RECYCLER_FOOTER){
            return;
        }

        mAdapter.onDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        boolean clickHeader = ((CustomRecyclerAdapter) mAdapter).hasHeaderView() && viewHolder.getAdapterPosition() == 0;
        boolean clickFooter = ((CustomRecyclerAdapter) mAdapter).hasFootView() && viewHolder.getAdapterPosition() == ((CustomRecyclerAdapter)mAdapter).getItemCount() - 1;
        boolean clickRecyclerFooter = ((CustomRecyclerAdapter) mAdapter).getPlugAdapter().getItemViewType(viewHolder.getAdapterPosition() - ((CustomRecyclerAdapter) mAdapter).getHeaderViewCount()) == CustomRecyclerView.TYPE_RECYCLER_FOOTER;
        if(clickHeader || clickFooter || clickRecyclerFooter){
            return;
        }else{
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                float width = (float) viewHolder.itemView.getWidth();
                float alpha = 1.0f - Math.abs(dX) / width;
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                        actionState, isCurrentlyActive);
            }
        }
    }
}
