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
public class NetPresenter<V extends INetContact.View> extends BasePresenter<V> implements IRequest {
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
    public INetCancel single(Wrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        INetCancel iNetCancel = mRequest.single(wrapper);
        return managerNetCancel(wrapper, iNetCancel);
    }

    @Override
    public INetCancel parallel(Wrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        INetCancel iNetCancel = mRequest.parallel(wrapper);
        return managerNetCancel(wrapper, iNetCancel);
    }

    @Override
    public INetCancel serial(Wrapper wrapper) {
        if (Preconditions.isNull(wrapper)) return null;
        INetCancel iNetCancel = mRequest.serial(wrapper);
        return managerNetCancel(wrapper, iNetCancel);
    }

    private INetCancel managerNetCancel(Wrapper request, INetCancel iNetCancel) {
        mCancels.add(iNetCancel);
        if (null != request)
            request.callback(new CallbackAdapter(request.getCallback()) {
                @Override
                public void onStart() {
                    if (null != request) request.state(IConst.STATE_START);
                    super.onStart();
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
     * @param type      串行、并行
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
