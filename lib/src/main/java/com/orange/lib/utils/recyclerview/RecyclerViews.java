package com.orange.lib.utils.recyclerview;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.orange.lib.utils.base.Preconditions;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:25
 */
public class RecyclerViews {
    public static boolean isBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        //屏幕中最后一个可见子项的position
        int lastVisibleItemPosition = getLastPosition(recyclerView);
        //当前屏幕所看到的子项个数
//            int visibleItemCount = layoutManager.getChildCount();

        //当前RecyclerView的所有子项个数
        int totalItemCount = layoutManager.getItemCount();
        boolean isLastPosition = lastVisibleItemPosition == totalItemCount - 1;


        //RecyclerView的滑动状态
        boolean isIdle = recyclerView.SCROLL_STATE_IDLE == recyclerView.getScrollState();//是否非滑动

        boolean canScrollUp = recyclerView.canScrollVertically(-1);//是否能向上滑
        return isLastPosition && isIdle && !canScrollUp;
    }

    /**
     * 获取列表最后position
     *
     * @param recyclerView
     * @return
     */
    private static int getLastPosition(RecyclerView recyclerView) {
        if (Preconditions.isNull(recyclerView)) return -1;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int lastPosition = 0;
        if (layoutManager instanceof GridLayoutManager) {
            //通过LayoutManager找到当前显示的最后的item的position
            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof LinearLayoutManager) {
            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
            lastPosition = findMax(lastPositions);
        }
        return lastPosition;
    }

    /**
     * 找到数组中的最大值
     *
     * @param lastPositions
     * @return
     */
    private static int findMax(int[] lastPositions) {
        if (Preconditions.isEmpty(lastPositions)) return -1;
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
