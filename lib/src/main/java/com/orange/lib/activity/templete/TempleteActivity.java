package com.orange.lib.activity.templete;

import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.orange.lib.R;
import com.orange.lib.mvp.contact.view.NetActivity;
import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.view.Views;

public abstract class TempleteActivity extends NetActivity {

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
        super.attachStub();

        //content占位
        attachContent();
    }

    protected void attachContent() {
        //content占位
        if (!Preconditions.isNull(mHolder)) {
            Views.attachStub(mHolder.getView(R.id.id_stub_content_orange), getContentLayoutId());
        }
    }

    /**
     * 获取模板布局文件
     *
     * @return
     */
    protected abstract int getTempleteLayoutId();
}
