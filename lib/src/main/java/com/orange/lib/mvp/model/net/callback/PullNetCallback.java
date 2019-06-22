package com.orange.lib.mvp.model.net.callback;


import com.orange.lib.component.pull.IPull;

public class PullNetCallback<T> extends DefaultNetCallback<T> {
    private IPull pull;

    public PullNetCallback(IPull pull) {
        this.pull = pull;
    }

    @Override
    public void onComplete(boolean noData, boolean empty) {
        super.onComplete(noData, empty);
        if (null != pull) {
            pull.finishRefresh(noData);
            pull.finishLoadmore(noData);
        }
    }
}
