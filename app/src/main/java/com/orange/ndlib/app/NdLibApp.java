package com.orange.ndlib.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.orange.lib.app.BaseApplication;
import com.orange.lib.common.adapterpattern.ActivityLifecycleAdapter;
import com.orange.lib.common.config.Config;
import com.orange.lib.mvp.contact.view.BaseActivity;
import com.orange.thirdparty.logger.LoggerImpl;
import com.orange.utils.base.Utils;

public class NdLibApp extends BaseApplication {
    @Override
    public void init(Context context) {
        Utils.init(context);
//        Config.getInstance().configNet(RetrofitRequest.newInstance());
        LoggerImpl logger = new LoggerImpl();
        logger.init();
        Config.getInstance().configLog(logger);
        registerActivityLifecycleCallbacks(new ActivityLifecycleAdapter() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if (activity instanceof BaseActivity)
                    ((BaseActivity) activity).onActivityCreated(bundle);
            }
        });
    }
}
