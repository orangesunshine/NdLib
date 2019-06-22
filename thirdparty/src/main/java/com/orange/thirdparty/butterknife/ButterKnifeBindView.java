package com.orange.thirdparty.butterknife;

import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.orange.lib.common.bind.IBindView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ButterKnifeBindView implements IBindView {

    private Unbinder mUnbinder;

    @Override
    public void bindViews(View view) {
        if (null != view)
            mUnbinder = ButterKnife.bind(ActivityUtils.getActivityByView(view));
    }

    @Override
    public void unbindView() {
        if (null != mUnbinder)
            mUnbinder.unbind();
    }
}
