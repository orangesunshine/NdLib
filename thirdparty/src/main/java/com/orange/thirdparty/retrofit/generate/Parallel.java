package com.orange.thirdparty.retrofit.generate;

import com.orange.lib.utils.base.Preconditions;
import com.orange.lib.utils.log.Logs;
import com.orange.thirdparty.retrofit.params.RetrofitParams;

import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import okhttp3.ResponseBody;

public class Parallel<T> extends Single implements IGenerate<T> {
    protected LinkedList<RetrofitParams> mParallelParams;
    protected BiFunction mBiFunction;
    protected Function3 mFunction3;
    protected Function4 mFunction4;

    public Parallel(RetrofitParams params1, RetrofitParams params2, BiFunction biFunction) {
        if (null == mParallelParams) mParallelParams = new LinkedList<>();
        mParallelParams.add(params1);
        mParallelParams.add(params2);
        mBiFunction = biFunction;
    }

    public Parallel(RetrofitParams params1, RetrofitParams params2, RetrofitParams params3, Function3 function3) {
        if (null == mParallelParams) mParallelParams = new LinkedList<>();
        mParallelParams.add(params1);
        mParallelParams.add(params2);
        mParallelParams.add(params3);
        mFunction3 = function3;
    }

    public Parallel(RetrofitParams params1, RetrofitParams params2, RetrofitParams params3, RetrofitParams params4, Function4 function4) {
        if (null == mParallelParams) mParallelParams = new LinkedList<>();
        mParallelParams.add(params1);
        mParallelParams.add(params2);
        mParallelParams.add(params3);
        mParallelParams.add(params4);
        mFunction4 = function4;
    }

    public void parallelParams(RetrofitParams params) {
        if (null != params) {
            if (null == mParallelParams) mParallelParams = new LinkedList<>();
            mParallelParams.add(params);
        }
    }

    public void setBiFunction(BiFunction biFunction) {
        mBiFunction = biFunction;
    }

    public void setFunction3(Function3 function3) {
        mFunction3 = function3;
    }

    public void setFunction4(Function4 function4) {
        mFunction4 = function4;
    }

    @Override
    public Observable<T> generateObservable() {
        if (Preconditions.isEmpty(mParallelParams)) return null;
        try {
            return parallel(mParallelParams);
        } catch (Exception e) {
            Logs.e("error: " + e.getMessage());
        }
        return null;
    }

    public <T> Observable<T> parallel(LinkedList<RetrofitParams> paramsList) throws IllegalAccessException {
        Preconditions.removeNull(paramsList);//清空null元素
        int size = paramsList.size();
        switch (size) {
            case 1:
                throw new IllegalArgumentException("request request not support,please use RetrofitParams deal");
            case 2:
                return parallel2(paramsList);
            case 3:
                return parallel3(paramsList);
            case 4:
                return parallel4(paramsList);
            default:
                break;
        }
        return null;
    }

    public <T> Observable<T> parallel2(LinkedList<RetrofitParams> paramsList) throws IllegalAccessException {
        if (Preconditions.isEmpty(paramsList) || Preconditions.condition(2 != paramsList.size()))
            return null;
        Observable<ResponseBody> observable1 = paramsList.poll().generateObservable();
        Observable<ResponseBody> observable2 = paramsList.poll().generateObservable();
        return Observable.zip(observable1, observable2, mBiFunction);
    }

    public <T> Observable<T> parallel3(LinkedList<RetrofitParams> paramsList) throws IllegalAccessException {
        if (Preconditions.isEmpty(paramsList) || Preconditions.condition(3 != paramsList.size()))
            return null;
        Observable<ResponseBody> observable1 = paramsList.poll().generateObservable();
        Observable<ResponseBody> observable2 = paramsList.poll().generateObservable();
        Observable<ResponseBody> observable3 = paramsList.poll().generateObservable();
        return Observable.zip(observable1, observable2, observable3, mFunction3);
    }

    public <T> Observable<T> parallel4(LinkedList<RetrofitParams> paramsList) throws IllegalAccessException {
        if (Preconditions.isEmpty(paramsList) || Preconditions.condition(4 != paramsList.size()))
            return null;
        Observable<ResponseBody> observable1 = paramsList.poll().generateObservable();
        Observable<ResponseBody> observable2 = paramsList.poll().generateObservable();
        Observable<ResponseBody> observable3 = paramsList.poll().generateObservable();
        Observable<ResponseBody> observable4 = paramsList.poll().generateObservable();
        return Observable.zip(observable1, observable2, observable3, observable4, mFunction4);
    }
}

