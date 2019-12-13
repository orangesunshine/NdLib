package com.orange.ndlib.app;

import android.content.Context;

import com.orange.lib.app.BaseApplication;
import com.orange.utils.base.Utils;

public class NdLibApp extends BaseApplication {
    @Override
    public void init(Context context) {
        Utils.init(context);
//        Config.getInstance().configNet(RetrofitRequest.newInstance());
    }
}
