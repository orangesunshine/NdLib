package com.orange.thirdparty.butterknife;

import android.view.View;

import com.orange.lib.common.bind.IBindView;

import butterknife.Unbinder;

public class ButterKnifeBindView implements IBindView {

    private Unbinder mUnbinder;

    @Override
    public void bindViews(View view) {
//        if (null != view)
//            mUnbinder = ButterKnife.bind(Activitys.getActivityByView(view));
    }

    @Override
    public void unbindView() {
        if (null != mUnbinder)
            mUnbinder.unbind();
    }
}
