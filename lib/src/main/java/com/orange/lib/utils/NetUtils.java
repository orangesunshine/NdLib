package com.orange.lib.utils;


import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.common.convert.IConvert;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoading;
import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.DefaultPullCallback;
import com.orange.lib.component.pull.swipe.IFooter;
import com.orange.lib.component.pull.swipe.SwipeRefreshLoadmore;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.mvp.model.net.INetRequest;
import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.callback.LoadingNetCallback;
import com.orange.lib.mvp.model.net.callback.SwipPullNetCallback;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;

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
    public static <T> void loadingNetData(ILoading loading, INetRequest netRequest, IConvert<T> convert) {
        netRequest.request(new LoadingNetCallback<T>(loading) {
            @Override
            public void onSuccess(T t) {
                super.onSuccess(t);
                if (null != convert) {
                    convert.convert(t);
                }
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
    public static <T> void loadingNetData(INetRequest<T> netRequest, INetCallback<T> netCallback) {
        netRequest.request(netCallback);
    }
    // </editor-fold>

    /**
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void pullAdapterNetData(IRefreshLoadmore refreshLoadmore, IPageNetRequest<T> pageNetRequest, INetCallback<T> callback) {
        if (null == refreshLoadmore || null == pageNetRequest || null == callback)
            throw new NullPointerException("null == refreshLoadmore||null == pageNetRequest||null == callback");
        refreshLoadmore.setPullCallback(new DefaultPullCallback(pageNetRequest, callback));
    }

    /**
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void swipePullAdapterNetData(SwipeRefreshLayout refreshLayout, IFooter footer, RecyclerView recyclerView, View emptyView, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView<T> convertRecyclerView) {
        SwipeRefreshLoadmore swipeRefreshLoadmore = new SwipeRefreshLoadmore(refreshLayout, footer, recyclerView);
        swipeRefreshLoadmore.setPullCallback(new DefaultPullCallback(pageNetRequest, new SwipPullNetCallback(refreshLayout, footer, recyclerView, emptyView, itemLayoutId, convertRecyclerView)));
    }

    /**
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void swipePullAdapterNetData(IHolder holder, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView<T> convertRecyclerView) {
        SwipeRefreshLoadmore swipeRefreshLoadmore = new SwipeRefreshLoadmore(holder);
        swipeRefreshLoadmore.setPullCallback(new DefaultPullCallback(pageNetRequest, new SwipPullNetCallback(holder, itemLayoutId, convertRecyclerView)));
    }
}
