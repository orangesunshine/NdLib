package com.orange.lib.component.pull;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.orange.lib.R;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.pull.callback.AbstractPull;
import com.orange.lib.mvp.model.net.pull.IPageNetRequest;
import com.orange.lib.utils.ViewUtils;


public class SwipeRefreshLoadmore extends AbstractPull<SwipeRefreshLayout> implements IRefreshLoadmore {
    private TextView footer;

    public <T> SwipeRefreshLoadmore(IHolder holder, IPageNetRequest<T> pageRequest) {
        super(holder, pageRequest);
        refreshLayout.setOnRefreshListener(() -> onPullRefresh());
        footer = holder.getView(R.id.refreshlayout_footer);
    }

    @Override
    public void refresh() {
        resetNoData();
        if (null != refreshLayout)
            refreshLayout.setRefreshing(true);
    }

    @Override
    public void loadmore() {
        ViewUtils.setVisible(footer, true);
    }

    /**
     * 设置能否加载
     *
     * @param enable
     * @return
     */
    @Override
    public void enableLoadMore(boolean enable) {
        if (null != footer) {
            ViewGroup parent = (ViewGroup) footer.getParent();
            if (null != parent)
                parent.removeView(footer);
        }
    }

    private void resetNoData() {
        ViewUtils.setText(footer, R.string.loadmore_footer);
    }
}
