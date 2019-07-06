package com.orange.lib.utils;


import android.view.View;

import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.convert.IConvert;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.DefaultPullCallback;
import com.orange.lib.component.pull.swipe.DefaultFooter;
import com.orange.lib.component.pull.swipe.IFooter;
import com.orange.lib.component.pull.swipe.SwipeRefreshLoadmore;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.loading.callback.LoadingNetCallback;
import com.orange.lib.loading.pagestatus.IPageStatus;
import com.orange.lib.loading.request.INetRequest;
import com.orange.lib.loading.callback.PageStatusNetCallback;
import com.orange.lib.pull.callback.SwipePullNetCallback;
import com.orange.lib.mvp.model.net.common.netcancel.INetCancel;
import com.orange.lib.pull.request.IPageNetRequest;

public class NetUtils {

    // <editor-fold defaultstate="collapsed" desc="loading网络数据">

    /**
     * netRequest请求网络数据，默认LoadingNetCallback处理，convert自定义处理onSuccess返回
     *
     * @param loading
     * @param netRequest
     * @param convert
     * @param <T>
     */
    public static <T> INetCancel loadingNetData(INetRequest<T> netRequest, ILoadingDialog loading, IConvert<T> convert) {
        Preconditions.checkNotNull(netRequest);
        return netRequest.request(new LoadingNetCallback<T>(loading) {
            @Override
            public void onSuccess(T t) {
                super.onSuccess(t);
                if (null != convert)
                    convert.convert(t);
            }
        });
    }

    /**
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param netCallback
     * @param netRequest
     * @param <T>
     */
    public static <T> INetCancel loadingNetData(INetRequest<T> netRequest, INetCallback<T> netCallback) {
        Preconditions.checkNotNull(netRequest);
        return netRequest.request(netCallback);
    }

    /**
     * netRequest请求网络数据，pageStatus处理，convert自定义处理onSuccess返回
     *
     * @param pageStatus
     * @param netRequest
     * @param convert
     * @param <T>
     */
    public static <T> INetCancel loadingNetData(INetRequest<T> netRequest, IPageStatus pageStatus, IConvert<T> convert) {
        Preconditions.checkNotNull(netRequest);
        return netRequest.request(new PageStatusNetCallback<T>(pageStatus) {
            @Override
            public void onSuccess(T t) {
                super.onSuccess(t);
                if (null != convert)
                    convert.convert(t);
            }
        });
    }
    // </editor-fold>

    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> INetCancel swipePullAdapterNetData(IHolder holder, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView convertRecyclerView) {
        Preconditions.checkNotNull(pageNetRequest);
        SwipeRefreshLayout refreshLayout = holder.getView(R.id.refreshlayout);
        RecyclerView recyclerView = holder.getView(R.id.recyclerview);
        View emptyView = holder.getView(R.id.empty_id);
        IFooter footer = new DefaultFooter(holder);
        return swipePullAdapterNetData(refreshLayout, recyclerView, emptyView, itemLayoutId, pageNetRequest, convertRecyclerView);
    }

    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> INetCancel swipePullAdapterNetData(SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView convertRecyclerView) {
        IRefreshLoadmore refreshLoadmore = new SwipeRefreshLoadmore(refreshLayout, recyclerView);
        return refreshLoadmore.setPullCallback(new DefaultPullCallback(refreshLayout, pageNetRequest, new SwipePullNetCallback(refreshLayout, recyclerView, emptyView, itemLayoutId, convertRecyclerView)));
    }
}
