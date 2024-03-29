package com.orange.thirdparty.retrofit.responsebody.parse;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.orange.lib.common.config.Config;
import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Preconditions;
import com.orange.lib.utils.Reflections;
import com.orange.utils.common.Commons;
import com.orange.utils.common.Gsons;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

import static com.orange.lib.constance.IConst.LINE_SEPARATOR;

public class RbParser {
    private static RbParser mInstance;

    private RbParser() {
    }

    public static RbParser getInstance() {
        if (null == mInstance) {
            synchronized (RbParser.class) {
                if (null == mInstance)
                    mInstance = new RbParser();
            }
        }
        return mInstance;
    }

    public static <T> BaseResponse<T> parse(ResponseBody responseBody, ICallback<T> callback) {
        if (ICallback.class == Preconditions.needNotNull(callback).getClass())
            throw new IllegalArgumentException("parse not support ICallback primary, please use subclass or special class!");
        Type type = Reflections.getGenericActualTypeArg(callback.getClass());
        if (null == type)
            throw new IllegalArgumentException("please special " + callback.getClass() + " GenericInterfaces");
        return parse(responseBody, type);
    }

    public static <T> BaseResponse<T> parse(ResponseBody responseBody, Type type) {
        return getInstance().parseResponse(responseBody, type);
    }

    public <T> BaseResponse<T> parseResponse(ResponseBody responseBody, Type type) {
        String body = null;
        try {
            if (null == responseBody || TextUtils.isEmpty(body = responseBody.string()))
                return new BaseResponse<>("body is empty!");
        } catch (Exception e) {
            Config.getInstance().getLog().e(e);
        }
        return parseResponse(body, TypeToken.getParameterized(BaseResponse.class, type).getType());
    }

    public <T> BaseResponse<T> parseResponse(String json, Type type) {
        BaseResponse<T> result = null;
        try {
            result = Gsons.getGson().fromJson(json, type);
        } catch (Exception e) {
            Config.getInstance().getLog().e(e);
            if (null == result) result = new BaseResponse<>();
            if (null != e) {
                StringBuilder error = new StringBuilder();
                error.append(e.getMessage());
                Throwable cause = e.getCause();
                if (null != cause)
                    error.append("cause: ").append(cause.getMessage()).append(LINE_SEPARATOR);
                result.msg = error.toString();
            }
        } finally {
            if (!Commons.checkCodeSuccess(result.code) && result.msg.length() == 0) {
                result.msg = "未知异常";
            }
        }
        return result;
    }
}
