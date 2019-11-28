package com.orange.lib.utils.net;


import android.view.View;

import androidx.core.util.Preconditions;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.convert.IConvert;
import com.orange.lib.common.convert.IPullConvert;
import com.orange.lib.common.convert.PullConvert;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.common.reponse.PullData;
import com.orange.lib.component.recyclerview.IConvertRecyclerView;
import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.loading.callback.LoadingNetCallback;
import com.orange.lib.loading.callback.PageStatusNetCallback;
import com.orange.lib.loading.pagestatus.IPage;
import com.orange.lib.loading.request.INetRequest;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.view.ifc.ILoading;
import com.orange.lib.pull.pagestatus.IPullPageStatus;
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
    public static <T> INetCancel loadingNetData(INetRequest<T> netRequest, ILoading loading, IConvert<T> convert) {
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
    public static <T> INetCancel loadingNetData(INetRequest<T> netRequest, IPage pageStatus, IConvert<T> convert) {
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

    public static <ITEM> INetCancel swipePullPageAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, IPullPageStatus pageStatus, IHolder holder, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        return swipePullPageAdapterNetData(pageNetRequest, pageStatus, holder.getView(R.id.id_refreshlayout_orange), holder.getView(R.id.id_recyclerview_orange), holder.getView(R.id.id_empty_orange), itemLayoutId, convertRecyclerView);
    }

    public static <ITEM> INetCancel swipePullPageAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, IPullPageStatus pageStatus, IHolder holder, IPullConvert<ITEM> pullConvert) {
        return swipePullPageAdapterNetData(pageNetRequest, pageStatus, holder.getView(R.id.id_refreshlayout_orange), holder.getView(R.id.id_recyclerview_orange), pullConvert);
    }

    public static <ITEM> INetCancel swipePullPageAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, IPullPageStatus pageStatus, SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        return swipePullPageAdapterNetData(pageNetRequest, pageStatus, refreshLayout, recyclerView, new PullConvert(refreshLayout, recyclerView, emptyView, itemLayoutId, convertRecyclerView));
    }

    public static <ITEM> INetCancel swipePullPageAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, IPullPageStatus pageStatus, SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, IPullConvert<ITEM> pullConvert) {
        Preconditions.checkNotNull(pageNetRequest);
//        IRefreshLoadmore refreshLoadmore = new SwipeRefreshLoadmore(refreshLayout, recyclerView);
//        return refreshLoadmore.setPullCallback(new DefaultPullCallback(pageNetRequest, refreshLayout, new PageStatusPullNetCallback(pageStatus, pullConvert)));
        return null;
    }

    /**
     * wipe下拉、加载、网络请求、CommanAdapter处理结果（holder获取控件）
     *
     * @param pageNetRequest
     * @param holder
     * @param itemLayoutId
     * @param convertRecyclerView
     * @param <ITEM>
     * @return
     */
    public static <ITEM> INetCancel swipePullAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, IHolder holder, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        return swipePullAdapterNetData(pageNetRequest, holder.getView(R.id.id_refreshlayout_orange), holder.getView(R.id.id_recyclerview_orange), holder.getView(R.id.id_empty_orange), itemLayoutId, convertRecyclerView);
    }

    /**
     * swipe下拉、加载、网络请求、回调处理结果（holder获取控件）
     *
     * @param pageNetRequest
     * @param holder
     * @param pullConvert
     * @param <ITEM>
     * @return
     */
    public static <ITEM> INetCancel swipePullAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, IHolder holder, IPullConvert<ITEM> pullConvert) {
        return swipePullAdapterNetData(pageNetRequest, holder.getView(R.id.id_refreshlayout_orange), (RecyclerView) holder.getView(R.id.id_recyclerview_orange), pullConvert);
    }


    /**
     * wipe下拉、加载、网络请求、CommanAdapter处理结果
     *
     * @param pageNetRequest
     * @param refreshLayout
     * @param recyclerView
     * @param emptyView
     * @param itemLayoutId
     * @param convertRecyclerView
     * @param <ITEM>
     * @return
     */
    public static <ITEM> INetCancel swipePullAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, View emptyView, int itemLayoutId, IConvertRecyclerView<ITEM> convertRecyclerView) {
        return swipePullAdapterNetData(pageNetRequest, refreshLayout, recyclerView, new PullConvert<>(refreshLayout, recyclerView, emptyView, itemLayoutId, convertRecyclerView));
    }

    /**
     * swipe下拉、加载、网络请求、回调处理结果
     *
     * @param pageNetRequest 网络请求
     * @param refreshLayout  下拉、加载
     * @param recyclerView   结果处理
     * @param pullConvert    结果处理
     * @param <ITEM>         列表项
     * @return
     */
    public static <ITEM> INetCancel swipePullAdapterNetData(IPageNetRequest<? extends PullData<ITEM>> pageNetRequest, SwipeRefreshLayout refreshLayout, RecyclerView recyclerView, IPullConvert<ITEM> pullConvert) {
        Preconditions.checkNotNull(pageNetRequest);
//        IRefreshLoadmore refreshLoadmore = new SwipeRefreshLoadmore(refreshLayout, recyclerView);
//        return refreshLoadmore.setPullCallback(new DefaultPullCallback(pageNetRequest, refreshLayout, new SwipePullNetCallback(refreshLayout, pullConvert)));
        return null;
    }
}
