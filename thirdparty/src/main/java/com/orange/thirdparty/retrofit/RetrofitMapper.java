package com.orange.thirdparty.retrofit;

import com.orange.lib.utils.Preconditions;
import com.orange.thirdparty.rxjava.params.generate.IGenerateObservable;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

public abstract class RetrofitMapper<T> implements Function<T, Observable> {
    protected IGenerateObservable generate;

    public void setGenerate(IGenerateObservable generate) {
        this.generate = generate;
    }

    @Override
    public Observable apply(T t) throws Exception {
        convert(t, generate);
        return Preconditions.needNotNull(generate).getObservable();
    }

    public abstract void convert(T t, IGenerateObservable generate);
}
