package com.orange.lib.mvp.model.net.callback;


import android.view.View;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.utils.ViewUtils;

public class SwipPullNetCallback<T> extends DefaultNetCallback<T> {
    private SwipeRefreshLayout refreshLayout;
    private View footer;
    private boolean noData;

    public SwipPullNetCallback(IHolder holder) {
        refreshLayout = holder.getView(R.id.refreshlayout);
        footer = holder.getView(R.id.refreshlayout_footer);
    }

    @Override
    public void onComplete(boolean noData, boolean empty) {
        super.onComplete(noData, empty);
        noData(noData);
        if (null != refreshLayout) {
            refreshLayout.setRefreshing(false);
            ViewUtils.setVisible(footer, false);
        }
    }

    /**
     * 没有更多数据
     *
     * @param noData
     */
    private void noData(boolean noData) {
        if (this.noData != noData) {
            this.noData = noData;
//            if (null != mHolder)
//                mHolder.setText(R.id.refreshlayout_footer, noData ? R.string.no_more_data : R.string.loadmore_footer);
        }
    }
}
