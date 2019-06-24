package com.orange.lib.utils;


import com.orange.lib.common.convert.IConvert;
import com.orange.lib.component.pagestatus.loading.dialogfragment.ILoading;
import com.orange.lib.mvp.model.net.INetRequest;
import com.orange.lib.mvp.model.net.callback.INetCallback;
import com.orange.lib.mvp.model.net.callback.LoadingNetCallback;

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
}
