package com.orange.lib.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.orange.lib.activity.BaseActivity;
import com.orange.lib.common.adapterpattern.ActivityLifecycleAdapt;

public abstract class BaseApplication extends Application implements IApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化变量
        Context context = getApplicationContext();
        initGlobleVars(context);
        //初始化三方
        initParty(context);
        //监控actvity生命周期，回调方法
        registerActivityLifecycleCallbacks(new ActivityLifecycleAdapt() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                if(activity instanceof BaseActivity)
                    ((BaseActivity) activity).onActivityCreate(bundle);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if(activity instanceof BaseActivity)
                    ((BaseActivity) activity).onActivityDestroy();
            }
        });
    }

    @Override
    public void initGlobleVars(Context context) {
    }

    @Override
    public Application getApplication() {
        return this;
    }
}
