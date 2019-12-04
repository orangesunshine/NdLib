package com.orange.ndlib.activity.demo.base.status;

import android.view.View;
import android.widget.SeekBar;

import com.orange.lib.common.holder.IHolder;
import com.orange.lib.mvp.contact.view.BaseActivity;
import com.orange.lib.utils.toast.Toasts;
import com.orange.ndlib.R;
import com.orange.utils.common.Bars;
import com.orange.utils.common.Colors;

public class AndroidUtilCodeImlActivity extends BaseActivity {
    private int statusbarColor;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_statusbar_implby_androidutilcode;
    }

    @Override
    protected void statusBar() {
        statusbarColor = translucentStatusBar() ? getResources().getColor(R.color.transparent) : Colors.getRandomColor();
        Bars.setStatusBarColor(mActivity, statusbarColor);
        Bars.addMarginTopEqualStatusBarHeight(mHolder.getView(R.id.ll_root));
    }

    @Override
    protected void init() {
        super.init();
        mHolder.addOnItemChildClick(new IHolder.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(View v) {
                statusbarColor = Colors.getRandomColor();
                Toasts.showError("statusbarColor: " + statusbarColor);
                updateAlphaProgress(statusbarColor >>> 24);
                Bars.setStatusBarColor(mActivity, statusbarColor);
            }
        }, R.id.btn_color);
        SeekBar skAlpha = mHolder.getView(R.id.sk_progress_alpha);
        skAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                statusbarColor = Colors.setAlphaComponent(statusbarColor, progress);
                Bars.setStatusBarColor(mActivity, statusbarColor);
                updateAlphaProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateAlphaProgress(int progress) {
        mHolder.setText(R.id.tv_alpha_progress, "progress: " + Integer.toHexString(progress));
    }
}
