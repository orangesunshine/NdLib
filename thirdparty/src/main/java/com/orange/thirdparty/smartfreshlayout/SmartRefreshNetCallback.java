package com.orange.thirdparty.smartfreshlayout;


import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.model.net.callback.DefaultNetCallback;
import com.orange.thirdparty.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class SmartRefreshNetCallback<T> extends DefaultNetCallback<T> {
    private SmartRefreshLayout refreshLayout;

    public SmartRefreshNetCallback(IHolder holder) {
        refreshLayout = holder.getView(R.id.refreshlayout);
    }

    @Override
    public void onComplete(boolean noData, boolean empty) {
        super.onComplete(noData, empty);
        if (null != refreshLayout) {
            if (noData) {
                refreshLayout.finishRefreshWithNoMoreData();
                refreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        }
    }
}
