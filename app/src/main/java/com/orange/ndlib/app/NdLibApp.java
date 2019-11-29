package com.orange.ndlib.app;

import android.content.Context;

import com.orange.lib.app.BaseApplication;
import com.orange.lib.common.config.Config;
import com.orange.lib.utils.base.Utils;
import com.orange.thirdparty.retrofit.api.RetrofitRequest;

public class NdLibApp extends BaseApplication {
    @Override
    public void init(Context context) {
        Utils.init(context);
        Config.getInstance().configNet(RetrofitRequest.netInstance());
    }
}
