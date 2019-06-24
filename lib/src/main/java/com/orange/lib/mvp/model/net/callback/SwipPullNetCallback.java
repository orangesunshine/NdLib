package com.orange.lib.mvp.model.net.callback;


import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pull.swipe.IFooter;

public class SwipPullNetCallback<T> extends DefaultNetCallback<T> {
    private SwipeRefreshLayout refreshLayout;
    private IFooter mFooter;

    public SwipPullNetCallback(IHolder holder, IFooter footer) {
        refreshLayout = holder.getView(R.id.refreshlayout);
        mFooter = footer;
    }

    @Override
    public void onComplete(boolean noData, boolean empty) {
        super.onComplete(noData, empty);
        if (null != refreshLayout) {
            refreshLayout.setRefreshing(false);
            if (noData) {
                mFooter.showNodata();
            } else {
                mFooter.showComplete();
            }

        }
    }
}
