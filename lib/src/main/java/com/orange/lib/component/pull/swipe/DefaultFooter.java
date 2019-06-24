package com.orange.lib.component.pull.swipe;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.util.Preconditions;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;

public class DefaultFooter implements IFooter, Runnable {
    private final int DEFAULT_DELAY = 200;
    private IHolder mHolder;

    public DefaultFooter(IHolder holder) {
        Preconditions.checkNotNull(holder);
        mHolder = holder;
    }

    @Override
    public void showLoading() {
        mHolder.setVisible(R.id.fl_loading, true);
        mHolder.setVisible(R.id.rl_loading, true);
        mHolder.setVisible(R.id.tv_complete, false);
        mHolder.setVisible(R.id.tv_nodata, false);
    }

    @Override
    public void showNodata() {
        mHolder.setVisible(R.id.fl_loading, true);
        mHolder.setVisible(R.id.rl_loading, false);
        mHolder.setVisible(R.id.tv_complete, false);
        mHolder.setVisible(R.id.tv_nodata, true);
        dismiss();
    }

    @Override
    public void showComplete() {
        mHolder.setVisible(R.id.fl_loading, true);
        mHolder.setVisible(R.id.rl_loading, false);
        mHolder.setVisible(R.id.tv_complete, true);
        mHolder.setVisible(R.id.tv_nodata, false);
        dismiss();
    }

    public void dismiss() {
        mHolder.getView(R.id.fl_loading).postDelayed(this, DEFAULT_DELAY);
    }

    @Override
    public void disableLoadmore() {
        View footer = mHolder.getView(R.id.fl_loading);
        if (null != footer) {
            ViewGroup parent = (ViewGroup) footer.getParent();
            if (null != parent)
                parent.removeView(footer);
        }
    }

    @Override
    public void run() {
        mHolder.setVisible(R.id.fl_loading, false);
    }
}
