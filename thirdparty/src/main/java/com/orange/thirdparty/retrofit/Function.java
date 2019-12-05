package com.orange.thirdparty.retrofit;

import java.util.Observable;

public interface Function<R, T> {
    Observable apply(R from, T to);
}
