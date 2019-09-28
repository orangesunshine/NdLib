package com.orange.ndlib.activity.base.loading;

import android.view.View;

import com.orange.lib.activity.Retry;
import com.orange.lib.common.adapterpattern.NetCallbackAdapter;
import com.orange.lib.common.holder.IHolder;
import com.orange.lib.loading.callback.INetCallback;
import com.orange.lib.loading.callback.LoadingNetCallback;
import com.orange.lib.loading.pagestatus.IPageStatus;
import com.orange.lib.loading.pagestatus.LoadingDialogPageStatus;
import com.orange.lib.mvp.view.activity.NetActivity;
import com.orange.lib.utils.ReflectionUtils;
import com.orange.lib.utils.toast.Toasts;
import com.orange.ndlib.R;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public class BaseActivityLoadingDemo extends NetActivity<ILoadingDemoPresenter> implements ILoadingDemoView {

    /**
     * 获取布局文件
     *
     * @return
     */
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_base_loading_demo;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void init() {
        super.init();
        INetCallback<String> netCallback = new LoadingNetCallback<String>(this);
        NetCallbackAdapter objectNetCallbackAdapter = new NetCallbackAdapter(netCallback);
        Type genericInterfacesActualTypeArg = ReflectionUtils.getGenericInterfacesActualTypeArg(objectNetCallbackAdapter.getClass());
        String name = ((TypeVariable) genericInterfacesActualTypeArg).getName();
        GenericDeclaration genericDeclaration = ((TypeVariable) genericInterfacesActualTypeArg).getGenericDeclaration();
        TypeVariable<?>[] typeParameters = genericDeclaration.getTypeParameters();
        IPageStatus pageStatus = new LoadingDialogPageStatus(mLoading, mHolder);
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_content:
                        pageStatus.showContent();
                        break;
                    case R.id.btn_loading:
                        pageStatus.showLoading();
                        break;
                    case R.id.btn_empty:
                        pageStatus.showEmpty();
                        break;
                    case R.id.btn_error:
                        pageStatus.showError();
                        break;
                    case R.id.btn_loading_convert:
                        mPresenter.getLoadingData();
                        break;
                    case R.id.btn_loading_page:
                        mPresenter.getPullData();
                        break;
                    case R.id.btn_loading_page_convert:
                        mPresenter.getDatas();
                        break;
                }
            }
        }, R.id.btn_content, R.id.btn_loading, R.id.btn_empty, R.id.btn_error, R.id.btn_loading_convert, R.id.btn_loading_page, R.id.btn_loading_page_convert);
    }

    @Override
    protected ILoadingDemoPresenter getPresenter() {
        return new LoadingDemoPresenter();
    }

    @Retry
    public void test() {
        Toasts.showMsg("test");
    }

    @Override
    public void setLoadingDatas(String data) {
        mHolder.setText(R.id.tv_loding_content, data);
    }

    @Override
    public void setPullDatas(List<String> data) {
        mHolder.setText(R.id.tv_pull_content, data.toString());
    }
}
