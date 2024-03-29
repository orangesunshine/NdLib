package com.orange.lib.mvp.model.net.request;

import androidx.annotation.IntDef;


import com.orange.lib.mvp.model.net.callback.loading.ICallback;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IPullNetRequest<T> extends IPageNetRequest<T> {
    int TYPE_REFRESH = 0x001;
    int TYPE_LOADMORE = 0x002;

    /**
     * 下拉，加载请求网络
     *
     * @param type
     * @param callback
     */
    void pullReqeust(@PullType int type, ICallback<T> callback);

    @IntDef({TYPE_REFRESH, TYPE_LOADMORE})
    @Retention(RetentionPolicy.SOURCE)
    @interface PullType {
    }
}
