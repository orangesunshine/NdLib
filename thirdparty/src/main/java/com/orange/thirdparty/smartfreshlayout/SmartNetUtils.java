package com.orange.thirdparty.smartfreshlayout;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.DefaultPullCallback;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;
import com.orange.thirdparty.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class SmartNetUtils {
    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void smartPullAdapterNetData(IHolder holder, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView<T> convertRecyclerView) {
        SmartRefreshLayout refreshLayout = holder.getView(R.id.refreshlayout);
        RecyclerView recyclerView = holder.getView(R.id.recyclerview);
        View emptyView = holder.getView(R.id.empty_id);
        smartPullAdapterNetData(refreshLayout, recyclerView, emptyView, itemLayoutId, pageNetRequest, convertRecyclerView);
    }

    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void smartPullAdapterNetData(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView<T> convertRecyclerView) {
        IRefreshLoadmore refreshLoadmore = new SmartRefreshLoadmore(refreshLayout, recyclerView);
        refreshLoadmore.setPullCallback(new DefaultPullCallback(refreshLayout, pageNetRequest, new SmartRefreshNetCallback(refreshLayout, recyclerView, emptyView, itemLayoutId, convertRecyclerView)));
    }
}
