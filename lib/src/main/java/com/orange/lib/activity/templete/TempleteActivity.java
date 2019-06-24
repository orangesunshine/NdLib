package com.orange.lib.activity.templete;

import android.view.LayoutInflater;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.orange.lib.R;
import com.orange.lib.activity.BaseActivity;
import com.orange.lib.component.pagestatus.IPageStatus;

public abstract class TempleteActivity extends BaseActivity {
    protected IPageStatus mLoadingLayout;

    @Override
    protected void attachView(FrameLayout content) {
        int templeteLayoutId = getTempleteLayoutId();
        if (-1 == templeteLayoutId)
            throw new IllegalArgumentException("-1 == contentLayoutId");
        LayoutInflater.from(this).inflate(templeteLayoutId, content, true);
    }

    /**
     * 根据占位类型插入占位视图（actbar，pull，content）
     */
    @Override
    protected void attachStub() {
        //content占位
        ViewStub contentStub = mHolder.getView(R.id.stub_content);
        if (null != contentStub) {
            contentStub.setLayoutResource(getContentLayoutId());
            contentStub.inflate();
        }
        super.attachStub();
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
