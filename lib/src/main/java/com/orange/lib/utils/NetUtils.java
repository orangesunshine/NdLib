package com.orange.lib.utils;


import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.convert.IConvert;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pagestatus.IPageStatus;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoadingDialog;
import com.orange.lib.component.pull.IRefreshLoadmore;
import com.orange.lib.component.pull.callback.DefaultPullCallback;
import com.orange.lib.component.pull.swipe.DefaultFooter;
import com.orange.lib.component.pull.swipe.IFooter;
import com.orange.lib.component.pull.swipe.SwipeRefreshLoadmore;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.mvp.model.net.INetRequest;
import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.callback.LoadingNetCallback;
import com.orange.lib.mvp.model.net.callback.PageStatusNetCallback;
import com.orange.lib.mvp.model.net.callback.SwipePullNetCallback;
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
    public static <T> void loadingNetData(ILoadingDialog loading, INetRequest netRequest, IConvert<T> convert) {
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
     * netRequest请求网络数据，默认LoadingNetCallback处理，convert自定义处理onSuccess返回
     *
     * @param pageStatus
     * @param netRequest
     * @param convert
     * @param <T>
     */
    public static <T> void loadingNetData(IPageStatus pageStatus, INetRequest<T> netRequest, IConvert<T> convert) {
        netRequest.request(new PageStatusNetCallback<T>(pageStatus) {
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
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void swipePullAdapterNetData(IHolder holder, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView convertRecyclerView) {
        SwipeRefreshLayout refreshLayout = holder.getView(R.id.refreshlayout);
        RecyclerView recyclerView = holder.getView(R.id.recyclerview);
        View emptyView = holder.getView(R.id.empty_id);
        IFooter footer = new DefaultFooter(holder);
        swipePullAdapterNetData(refreshLayout, footer, recyclerView, emptyView, itemLayoutId, pageNetRequest, convertRecyclerView);
    }

    /**
     * SwipeRefreshLayout
     * netRequest请求网络数据，netCallback处理返回状态、结果
     *
     * @param <T>
     */
    public static <T> void swipePullAdapterNetData(SwipeRefreshLayout refreshLayout, IFooter footer, RecyclerView recyclerView, View emptyView, int itemLayoutId, IPageNetRequest<T> pageNetRequest, IConvertRecyclerView convertRecyclerView) {
        IRefreshLoadmore refreshLoadmore = new SwipeRefreshLoadmore(refreshLayout, footer, recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(refreshLayout.getContext()));
        refreshLoadmore.setPullCallback(new DefaultPullCallback(refreshLayout, pageNetRequest, new SwipePullNetCallback(refreshLayout, footer, recyclerView, emptyView, itemLayoutId, convertRecyclerView)));
    }
}
