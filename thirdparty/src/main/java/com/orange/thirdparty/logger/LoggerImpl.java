package com.orange.thirdparty.logger;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.orange.lib.utils.log.ILog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class LoggerImpl implements ILog {
    @Override
    public void setTag(String tag) {
        Logger.t(tag);
    }

    @Override
    public void init() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(4)         // (Optional) How many method line to show. Default 2
                .methodOffset(2)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    @Override
    public void d(@NonNull String message, @Nullable Object... args) {
        Logger.d(message, args);
    }

    @Override
    public void d(@Nullable Object object) {
        Logger.d(object);
    }

    @Override
    public void e(@NonNull String message, @Nullable Object... args) {
        Logger.e(message, args);
    }

    @Override
    public void e(@Nullable Throwable throwable) {
        e(throwable, "");
    }

    @Override
    public void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        Logger.e(throwable, message, args);
    }

    @Override
    public void w(@NonNull String message, @Nullable Object... args) {
        Logger.w(message, args);
    }

    @Override
    public void i(@NonNull String message, @Nullable Object... args) {
        Logger.i(message, args);
    }

    @Override
    public void v(@NonNull String message, @Nullable Object... args) {
        Logger.v(message, args);
    }

    @Override
    public void wtf(@NonNull String message, @Nullable Object... args) {
        Logger.wtf(message, args);
    }

    @Override
    public void json(@Nullable String json) {
        Logger.json(json);
    }

    @Override
    public void xml(@Nullable String xml) {
        Logger.xml(xml);
    }

    @Override
    public void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        Logger.log(priority, tag, message, throwable);
    }
}
