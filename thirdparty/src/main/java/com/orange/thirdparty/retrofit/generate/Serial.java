package com.orange.thirdparty.retrofit.generate;

import com.orange.lib.common.reponse.BaseResponse;
import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.log.Logs;
import com.orange.thirdparty.retrofit.params.SerialRetrofitParams;
import com.orange.thirdparty.rxjava.parse.ResponseBodyParser;
import com.orange.utils.common.Collections;

import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.ResponseBody;

public class Serial extends Single implements IGenerate<ResponseBody> {
    protected LinkedList<SerialRetrofitParams> serialParams;

    public Serial serialParams(SerialRetrofitParams serialObservable) {
        if (null != serialObservable) {
            if (null == serialParams) serialParams = new LinkedList<>();
            serialParams.add(serialObservable);
        }
        return this;
    }

    @Override
    public Observable<ResponseBody> generateObservable() {
        if (Collections.isEmpty(serialParams)) return null;
        try {
            return serial(serialParams);
        } catch (Exception e) {
            Logs.e("error: " + e.getMessage());
        }
        return null;
    }

    public <T> Observable<ResponseBody> serial(LinkedList<SerialRetrofitParams> paramsList) throws IllegalAccessException {
        if (Preconditions.isNull(paramsList)) return null;
        if (paramsList.size() < 1) throw new IllegalAccessException("paramsList.size()<1");
        SerialRetrofitParams poll = paramsList.poll();
        return poll.generateObservable().flatMap(new io.reactivex.functions.Function<ResponseBody, ObservableSource<ResponseBody>>() {
            @Override
            public ObservableSource<ResponseBody> apply(ResponseBody responseBody) throws Exception {
                SerialRetrofitParams tempParams = paramsList.peek();
                ResponseBodyParser responseBodyParser = new ResponseBodyParser(responseBody, poll.getType());
                BaseResponse response = responseBodyParser.parseResponse();
                if (null != tempParams)
                    tempParams.flatMapConvert(response);
                if (paramsList.size() < 2) return tempParams.generateObservable();
                return serial(paramsList);
            }
        });
    }
}
