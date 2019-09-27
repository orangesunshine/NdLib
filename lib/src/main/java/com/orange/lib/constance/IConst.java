package com.orange.lib.constance;

import android.widget.Toast;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public interface IConst {
    String sBaseUrl = "http://localhost:8080";
    String FRAGMENT_LOADING_DIALOG = "fragment_loading_dialog";//loadingDilog TAG

    //换行符
    String LINE_SEPARATOR = System.getProperty("line.separator");

    //日志默认tag
    String TAG_DEFAULT = "tag_default";

    ///////////////////////////////////////////////////////////////////////////
    // view相关
    ///////////////////////////////////////////////////////////////////////////
    //duration
    int DURATION_TOAST_DEFAULT = Toast.LENGTH_SHORT;
    int CUSTOM_DURATION_TOAST_DEFAULT = 2000;
    int CUSTOM_DURATION_TOAST_LONG = 5000;

    ///////////////////////////////////////////////////////////////////////////
    // 网络
    ///////////////////////////////////////////////////////////////////////////
    int REQUEST_BY_GET = 0x001;
    int REQUEST_BY_POST = 0x002;

    //网络请求结果code
    int CODE_SUCCESS = 200;
    int CODE_ERROR = -1;

    //pull每次请求数据条数
    int PULL_ITEM_COUNT = 10;

    //刷新|加载
    int TYPE_REFRESH = 0x001;
    int TYPE_LOADMORE = 0x002;

    @IntDef({TYPE_REFRESH, TYPE_LOADMORE})
    @Retention(RetentionPolicy.SOURCE)
    @interface PullType {
    }
}
