package com.orange.thirdparty.rxjava;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orange.lib.common.adapterpattern.CallbackAdapter;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.loading.ICallback;
import com.orange.lib.utils.CommonUtils;
import com.orange.lib.utils.ReflectionUtils;
import com.orange.lib.utils.base.Preconditions;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static com.orange.lib.constance.IConst.LINE_SEPARATOR;

public class LoadingResponseBodyObserver {
    private Gson mGson = new Gson();

    public static LoadingResponseBodyObserver newInstance() {
        return new LoadingResponseBodyObserver();
    }

    public <T> Disposable subscribe(Observable<ResponseBody> observable, ICallback<T> netCallback) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        //响应数据onNext
                        if (null == responseBody) return;
                        T result = null;
                        int code = IConst.CODE_ERROR;
                        StringBuilder errorMsg = new StringBuilder();
                        String responseMsg = "";
                        try {
                            String body = responseBody.string();
                            if (null == body) {
                                errorMsg.append("null == body");
                                return;
                            }
                            JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
                            if (null == jsonObject) {
                                errorMsg.append("null == jsonObject");
                                return;
                            }

                            JsonElement codeElement = jsonObject.get("code");
                            if (null != codeElement)
                                code = codeElement.getAsInt();
                            JsonElement msgElement = jsonObject.get("msg");
                            if (null != msgElement && !(msgElement instanceof JsonNull))
                                responseMsg = msgElement.getAsString();

                            JsonElement data = jsonObject.get("data");
                            if (null != data && !Preconditions.isNull(netCallback)) {
                                Type type = null;
                                if (netCallback instanceof ParameterizedType) {

                                    Type[] types = ((ParameterizedType) netCallback).getActualTypeArguments();
                                    if (null != types && types.length > 0) {
                                        type = types[0];
                                    }
                                }
                                if (null == type) {
                                    Class clazz = netCallback.getClass();
                                    if (netCallback instanceof CallbackAdapter) {
                                        ICallback<T> callback = ((CallbackAdapter<T>) netCallback).getNetCallback();
                                        if (null != callback) clazz = callback.getClass();
                                    }
                                    type = ReflectionUtils.getGenericActualTypeArg(clazz);
                                }
                                result = mGson.fromJson(data, type);
                            }
                        } catch (Exception e) {
                            if (null != e) {
                                errorMsg.append(e.getMessage());
                                Throwable cause = e.getCause();
                                if (null != cause)
                                    errorMsg.append("cause: ").append(cause.getMessage()).append(LINE_SEPARATOR);
                            }
                        } finally {
                            if (null != netCallback && null != result) {
                                if (CommonUtils.checkCodeSuccess(code)) {
                                    if (!TextUtils.isEmpty(responseMsg))
//                        ToastUtils.showShort(responseMsg);
                                        netCallback.onSuccess(result);
                                    return;
                                }
                                errorMsg.append("succuess code != 200");
                            }
                            if (null != netCallback) {
                                if (errorMsg.length() == 0) {
                                    errorMsg.append(TextUtils.isEmpty(responseMsg) ? "未知异常" : responseMsg);
                                }
                                netCallback.onError(code, new Throwable(errorMsg.toString()));
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //响应数据onError
                        if (null != netCallback) {
                            netCallback.onError(IConst.CODE_ERROR, throwable);
                            netCallback.onComplete();
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //响应数据onComplete
                        if (null != netCallback)
                            netCallback.onComplete();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        //响应数据onSubcribe
                        if (null != netCallback)
                            netCallback.onStart();
                    }
                });
    }
}
