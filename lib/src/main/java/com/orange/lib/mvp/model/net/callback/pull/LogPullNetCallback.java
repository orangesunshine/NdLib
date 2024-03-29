package com.orange.lib.mvp.model.net.callback.pull;

import android.os.SystemClock;

import com.orange.lib.constance.IInitConst;

import static com.orange.lib.constance.IConst.LINE_SEPARATOR;

/**
 * 网络请求回调默认实现
 *
 * @param <T>
 */
public class LogPullNetCallback<T> implements IPullNetCallback<T> {
    private StringBuilder log = new StringBuilder();
    private String indentation = "\u3000\u3000";//缩进
    private long startTimeMills = 0;

    /**
     * 网络请求成功
     *
     * @param t
     */
    @Override
    public void onSuccess(T t) {
        if (IInitConst.sRecordNetLogSwitch) {
            log.append("onSuccess: ");
            if (null != t) {
                log.append(LINE_SEPARATOR).append(indentation).append(t.toString());
            }
            log.append(LINE_SEPARATOR);
        }
    }

    /**
     * @param successs true:onSuccess,false:onError
     * @param noData   没有更多数据：用于pull
     * @param empty    结果数据为空
     */
    @Override
    public void onComplete(boolean successs, boolean noData, boolean empty) {
        if (IInitConst.sRecordNetLogSwitch) {
            log.append("onComplete: ");
            log.append("successs: ").append(successs).append("noData: ").append(noData).append("empty: ").append(empty);
            log.append(LINE_SEPARATOR).append(indentation).append("period: ").append(SystemClock.elapsedRealtime() - startTimeMills).append("ms");
//            LogUtils.error(log.toString());
        }
    }

    /**
     * 网络请求错误
     *
     * @param code
     * @param error
     */
    @Override
    public void onError(int code, Throwable error) {
        if (IInitConst.sRecordNetLogSwitch) {
            log.append("onError: ").append(indentation).append("code: ").append(code).append(indentation).append("errorMsg: ");
            StringBuilder errorMsg = new StringBuilder();
            if (null != error) {
                errorMsg.append(error.getMessage());
                Throwable cause = error.getCause();
                if (null != cause)
                    errorMsg.append(cause.getMessage());
//                if (errorMsg.length() > 0)
//                    ToastUtils.showShort(errorMsg);
            }
            log.append(errorMsg).append(LINE_SEPARATOR);
        }
    }
}
