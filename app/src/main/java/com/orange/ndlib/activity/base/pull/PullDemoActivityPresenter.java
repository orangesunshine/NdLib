package com.orange.ndlib.activity.base.pull;

import com.orange.lib.mvp.model.net.netcancel.INetCancel;
import com.orange.lib.mvp.presenter.PullPresenter;

/**
 * @Author: orange
 * @CreateDate: 2019/10/4 9:20
 */
public class PullDemoActivityPresenter extends PullPresenter<IPullDemoContact.View> implements IPullDemoContact.Presenter {
    @Override
    public INetCancel getPullDatas(boolean init) {
        return null;
    }
}
