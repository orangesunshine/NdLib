package com.orange.thirdparty.smartfreshlayout;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.IPageNetRequest;
import com.orange.thirdparty.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class SmartNetUtils {
    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> INetCancel smartPullAdapterNetData(IHolder holder, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView<T> convertRecyclerView) {
        SmartRefreshLayout refreshLayout = holder.getView(R.id.id_refreshlayout_orange);
        RecyclerView recyclerView = holder.getView(R.id.id_recyclerview_orange);
        View emptyView = holder.getView(R.id.id_empty_orange);
        return smartPullAdapterNetData(refreshLayout, recyclerView, emptyView, itemLayoutId, pageNetRequest, convertRecyclerView);
    }

    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> INetCancel smartPullAdapterNetData(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView<T> convertRecyclerView) {
//        IRefreshLoadmore refreshLoadmore = new SmartRefreshLoadmore(refreshLayout, recyclerView);
//        return refreshLoadmore.setPullCallback(new DefaultPullCallback(pageNetRequest, refreshLayout, new SmartRefreshNetCallback(refreshLayout, recyclerView, emptyView, itemLayoutId, convertRecyclerView)));
        return null;
    }
}
