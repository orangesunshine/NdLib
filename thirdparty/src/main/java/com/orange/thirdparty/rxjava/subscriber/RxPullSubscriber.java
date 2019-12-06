package com.orange.thirdparty.rxjava.subscriber;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.orange.lib.common.reponse.PullData;
import com.orange.lib.constance.IConst;
import com.orange.lib.mvp.model.net.callback.pull.IPullNetCallback;
import com.orange.lib.utils.Commons;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class RxPullSubscriber {
    private boolean mNoData = false;
    private boolean mEmpty = true;
    private Gson gson = new Gson();

    public static RxPullSubscriber newInstance() {
        return new RxPullSubscriber();
    }

    public <T> Disposable subsribe(Observable<ResponseBody> observable, Type type, IPullNetCallback<T> netCallback) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody){
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
                            if (null != data) {
                                if (null == type)
                                    throw new IllegalArgumentException("null == type");
                                TypeToken<T> typeToken = (TypeToken<T>) TypeToken.get(type);
                                TypeAdapter<T> adapter = gson.getAdapter(typeToken);
                                result = adapter.fromJson(data.toString());
                            }
                        } catch (Exception e) {
                            Throwable cause = e.getCause();
                            if (null != cause) {
                                errorMsg.append("cause: ");
                                errorMsg.append(cause.getMessage());
                                errorMsg.append(System.getProperty("line.separator"));
                            }
                        } finally {
                            if (null != result && result instanceof PullData) {
                                mEmpty = ((PullData) result).empty();
                                mNoData = ((PullData) result).noMoreData();
                            }
                            if (null != netCallback) {
                                if (null != result && Commons.checkCodeSuccess(code)) {
                                    if (!TextUtils.isEmpty(responseMsg) && TextUtils.isEmpty(errorMsg))
//                        ToastUtils.showShort(responseMsg);
                                        netCallback.onSuccess(result);
                                } else {
                                    if (errorMsg.length() == 0)
                                        errorMsg.append(TextUtils.isEmpty(responseMsg) ? "未知异常" : responseMsg);
                                    netCallback.onError(code, new Throwable(errorMsg.toString()));
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //响应数据onError
                        if (null != netCallback) {
                            netCallback.onError(IConst.CODE_ERROR, throwable);
                            netCallback.onComplete(false, true, true);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //响应数据onComplete
                        if (null != netCallback)
                            netCallback.onComplete(true, mNoData, mEmpty);
                    }
                });
    }
}
