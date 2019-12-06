package com.orange.lib.mvp.contact.presenter;

import com.orange.lib.common.adapterpattern.CallbackAdapter;
import com.orange.lib.common.config.Config;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.model.net.request.IRequest;
import com.orange.lib.mvp.model.net.request.request.Params;
import com.orange.lib.mvp.model.net.request.request.Wrapper;
import com.orange.lib.utils.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

import static com.orange.lib.constance.IConst.TYPE_SERIAL;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends INetContact.View, N extends Wrapper> extends BasePresenter<V> implements IRequest<N> {
    protected IRequest mRequest = Config.getInstance().getNet();
    protected List<INetCancel> mCancels = new ArrayList<>();//缓存网络请求接口，用于取消

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        if (!Preconditions.isEmpty(mCancels))
            for (INetCancel netCancel : mCancels) {
                if (Preconditions.isNull(netCancel)) continue;
                netCancel.cancel();
            }
        mCancels.clear();
        mCancels = null;
    }

    @Override
    public INetCancel request(N wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        INetCancel iNetCancel = mRequest.request(wrapper);
        return wrapStateNdNetcancel(wrapper, iNetCancel);
    }

    /**
     * 装饰：网络请求完成，删除对应网络取消
     *
     * @param request
     * @param iNetCancel
     * @return
     */
    private <T> INetCancel wrapStateNdNetcancel(N request, INetCancel iNetCancel) {
        mCancels.add(iNetCancel);
        if (null != request)
            request.callback(new CallbackAdapter<T>(request.getCallback()) {
                @Override
                public void onStart() {
                    if (null != request) request.state(IConst.STATE_START);
                    super.onStart();
                }

                @Override
                public void onSuccess(T t) {
                    if (null != request) request.state(IConst.STATE_ERROR);
                    super.onSuccess(t);
                }

                @Override
                public void onError(int code, Throwable error) {
                    if (null != request) request.state(IConst.STATE_SUCCESS);
                    super.onError(code, error);
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                    mCancels.remove(iNetCancel);
                }
            });
        return iNetCancel;
    }

    /**
     * 拼接多条网络请求
     *
     * @param type   串行、并行
     * @param params
     * @return
     */
    private String multiRequestUrl(int type, Params... params) {
        if (Preconditions.isNulls(params)) return null;
        StringBuffer buffer = new StringBuffer();
        buffer.append(TYPE_SERIAL == type ? "serial" : "parallel");
        for (Params request : params) {
            if (Preconditions.isNull(request) || Preconditions.isEmpty(request.getUrl())) continue;
            buffer.append(request.getUrl());
        }
        return buffer.toString();
    }
}
