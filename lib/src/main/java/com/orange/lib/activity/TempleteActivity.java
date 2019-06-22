package com.orange.lib.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.orange.lib.common.holder.DefaultHolder;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.component.actbar.IActionBar;
import com.orange.lib.component.loading.ILoadingLayout;
import com.orange.lib.component.pull.IPull;

public abstract class TempleteActivity extends BaseActivity {
    protected IActionBar mActbar;
    protected ILoadingLayout mLoadingLayout;
    protected IPull mPull;

    /**
     * onCreate生命周期调用
     *
     * @param bundle
     */
    @Override
    public void onActivityCreate(Bundle bundle) {
        FrameLayout content = getWindow().getDecorView().findViewById(android.R.id.content);
        int templeteLayoutId = getTempleteLayoutId();
        if (-1 == templeteLayoutId)
            throw new IllegalArgumentException("-1 == templeteLayoutId");
        LayoutInflater.from(mActivity).inflate(templeteLayoutId, content, true);
        mHolder = new DefaultHolder(content);
        initVars(bundle);
        templeteLayout(mHolder, templeteLayoutId);
        init();
    }

    /**
     * 处理模板布局文件
     */
    protected void templeteLayout(IHolder holder, int templeteContentLayoutId) {

    }

    /**
     * 获取模板布局文件
     *
     * @return
     */
    protected int getTempleteLayoutId() {
        return -1;
    }
}
