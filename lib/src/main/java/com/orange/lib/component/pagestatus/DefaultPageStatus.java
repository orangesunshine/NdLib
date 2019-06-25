package com.orange.lib.component.pagestatus;

import androidx.core.util.Preconditions;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;

public class DefaultPageStatus implements IPageStatus {
    private IHolder mHolder;

    public DefaultPageStatus(IHolder holder) {
        Preconditions.checkNotNull(holder);
        mHolder = holder;
        showContent();
    }

    /**
     * 显示loading
     */
    @Override
    public void showLoading() {
        mHolder.setVisible(R.id.loading_id, true);
        mHolder.setVisible(R.id.content_id, false);
        mHolder.setVisible(R.id.empty_id, false);
        mHolder.setVisible(R.id.error_id, false);
    }

    /**
     * 显示content
     */
    @Override
    public void showContent() {
        mHolder.setVisible(R.id.loading_id, false);
        mHolder.setVisible(R.id.content_id, true);
        mHolder.setVisible(R.id.empty_id, false);
        mHolder.setVisible(R.id.error_id, false);
    }

    /**
     * 显示empty
     */
    @Override
    public void showEmpty() {
        mHolder.setVisible(R.id.loading_id, false);
        mHolder.setVisible(R.id.content_id, false);
        mHolder.setVisible(R.id.empty_id, true);
        mHolder.setVisible(R.id.error_id, false);
    }

    /**
     * 显示error
     */
    @Override
    public void showError() {
        mHolder.setVisible(R.id.loading_id, false);
        mHolder.setVisible(R.id.content_id, false);
        mHolder.setVisible(R.id.empty_id, false);
        mHolder.setVisible(R.id.error_id, true);
    }
}
