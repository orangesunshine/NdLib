package com.orange.lib.app;

import android.app.Application;

public abstract class BaseApplication extends Application implements IApp {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        init(getApplicationContext());
    }
}
