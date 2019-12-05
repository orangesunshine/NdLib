package com.orange.lib.mvp.model.net.callback.loading;

import android.os.SystemClock;

import com.orange.lib.constance.IInitConst;
import com.orange.lib.utils.log.Logs;
import com.orange.lib.utils.toast.Toasts;

import static com.orange.lib.constance.IConst.LINE_SEPARATOR;

/**
 * 网络请求回调默认实现
 *
 * @param <T>
 */
public class LogCallback<T> extends AbsCallback<T> {
    private StringBuilder log = new StringBuilder();
    private String indentation = "\u3000\u3000";//缩进
    private long startTimeMills = 0;

    /**
     * 网络请求开始
     */
    @Override
    public void onStart() {
        if (IInitConst.sRecordNetLogSwitch) {
            log.append("onStart: ").append(LINE_SEPARATOR);
            startTimeMills = SystemClock.elapsedRealtime();
        }
    }

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
     * 网络请求完成
     */
    @Override
    public void onComplete() {
        super.onComplete();
        if (IInitConst.sRecordNetLogSwitch) {
            log.append("onComplete: ");
            log.append(LINE_SEPARATOR).append(indentation).append("period: ").append(SystemClock.elapsedRealtime() - startTimeMills).append("ms");
            Logs.i(log.toString());
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
                if (errorMsg.length() > 0)
                    Toasts.showNotice(errorMsg);
            }
            log.append(errorMsg).append(LINE_SEPARATOR);
        }
    }
}
