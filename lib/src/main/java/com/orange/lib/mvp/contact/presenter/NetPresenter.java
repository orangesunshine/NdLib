package com.orange.lib.mvp.contact.presenter;

import com.orange.lib.mvp.contact.INetContact;
import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.utils.base.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/9/27 16:54
 */
public class NetPresenter<V extends INetContact.View> extends BasePresenter<V> {
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
}
