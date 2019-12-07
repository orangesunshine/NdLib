package com.orange.thirdparty.rxjava.parse;

import android.text.TextUtils;

import com.google.gson.GsonBuilder;
import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.Commons;
import com.orange.lib.utils.Reflections;
import com.orange.thirdparty.rxjava.params.RxParams;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

import static com.orange.lib.constance.IConst.LINE_SEPARATOR;

public class RxParser {
    private ParameterizedTypeImpl parameterizedType;
    private ResponseBody mResponseBody;

    public static <T> BaseResponse<T> parse(ResponseBody responseBody, RxParams params) {
        if (null == params) throw new NullPointerException("null == params");
        return new RxParser(responseBody, params.getType()).parseResponse();
    }

    public static <T> BaseResponse<T> parse(ResponseBody responseBody, Type type) {
        return new RxParser(responseBody, type).parseResponse();
    }

    public static <T> BaseResponse<T> parse(ResponseBody responseBody, ICallback<T> callback) {
        return new RxParser(responseBody, callback).parseResponse();
    }

    public RxParser(ResponseBody responseBody, Type type) {
        parameterizedType = new ParameterizedTypeImpl(BaseResponse.class, new Type[]{type});
        mResponseBody = responseBody;
    }

    public <T> RxParser(ResponseBody responseBody, ICallback<T> callback) {
        if (ICallback.class == callback.getClass())
            throw new IllegalArgumentException("parse not support ICallback primary, please use subclass or special class!");
        Type type = Reflections.getGenericActualTypeArg(callback.getClass());
        if (null == type)
            throw new IllegalArgumentException("please special " + callback.getClass() + " GenericInterfaces");
        parameterizedType = new ParameterizedTypeImpl(BaseResponse.class, new Type[]{type});
        mResponseBody = responseBody;
    }

    public <T> BaseResponse<T> parseResponse() {
        BaseResponse<T> result = null;
        try {
            String body;
            if (null == mResponseBody || TextUtils.isEmpty(body = mResponseBody.string()))
                return new BaseResponse<>("body is empty!");
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            int len = actualTypeArguments.length;
            result = new GsonBuilder().create().fromJson(body, parameterizedType);
        } catch (Exception e) {
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
