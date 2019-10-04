package com.orange.ndlib.activity.base.loading.page;

import android.view.View;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.view.activity.page.PageNetActivity;
import com.orange.ndlib.R;

public class PageNetDemoActivity extends PageNetActivity<IPageDemoContact.Presenter> implements IPageDemoContact.View {
    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_show_content:
                        showContent();
                        break;
                    case R.id.btn_show_loading:
                        showLoading();
                        break;
                    case R.id.btn_show_empty:
                        showEmpty();
                        break;
                    case R.id.btn_show_error:
                        showError();
                        break;
                    case R.id.btn_data:
                        mPresenter.getLoadingData();
                        break;
                }
            }
        }, R.id.btn_show_content, R.id.btn_show_loading, R.id.btn_show_empty, R.id.btn_show_error, R.id.btn_data);
    }

    @Override
    protected IPageDemoContact.Presenter getPresenter() {
        return new PageNetDemoPresenter();
    }

    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_page_net_demo;
    }

    @Override
    public void setLoadingData(String data) {
        mHolder.setText(R.id.tv_content,data);
    }
}
