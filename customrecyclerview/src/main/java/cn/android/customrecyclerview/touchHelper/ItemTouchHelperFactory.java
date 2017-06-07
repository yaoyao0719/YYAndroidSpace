package cn.android.customrecyclerview.touchHelper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import cn.android.customrecyclerview.listener.ItemTouchAdapter;


/**
 * Created by yao on 16/6/12.
 */
public class ItemTouchHelperFactory {

    private static final String TAG = "ItemTouchHelperFactory";
    
    public static ItemTouchHelper.Callback createCallback(RecyclerView mRecycler, RecyclerView.LayoutManager manager, boolean shouldDrag, boolean shouldSwipe){
        if((manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) && mRecycler.getAdapter() instanceof ItemTouchAdapter){
            return new ItemTouchHelperCallback((ItemTouchAdapter)mRecycler.getAdapter(),shouldDrag,shouldSwipe);
        }else if(manager instanceof LinearLayoutManager && mRecycler.getAdapter() instanceof ItemTouchAdapter){
            return new SimpleItemTouchHelperCallback((ItemTouchAdapter)mRecycler.getAdapter(),shouldDrag,shouldSwipe);
        }

        throw new RuntimeException("invalid LayoutManager!it must be linear,grid or staggered");
    }
}
