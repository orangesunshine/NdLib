package com.orange.lib.utils.recyclerview;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:25
 */
public class RecyclerViews {
    public static boolean isVisBottom(RecyclerView recyclerView) {
        if (recyclerView != null) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //屏幕中最后一个可见子项的position
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            //当前屏幕所看到的子项个数
            int visibleItemCount = layoutManager.getChildCount();
            //当前RecyclerView的所有子项个数
            int totalItemCount = layoutManager.getItemCount();
            //RecyclerView的滑动状态
            int state = recyclerView.getScrollState();
            if (visibleItemCount > 0 && lastVisibleItemPosition == totalItemCount - 1 && state == recyclerView.SCROLL_STATE_IDLE) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
