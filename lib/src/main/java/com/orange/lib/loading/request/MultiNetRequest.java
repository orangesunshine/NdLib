package com.orange.lib.loading.request;

import java.util.LinkedList;
import java.util.List;

/**
 * @Author: orange
 * @CreateDate: 2019/9/28 13:56
 */
public class MultiNetRequest {
    private List<NetRequest> mNetRequests = new LinkedList<>();

    public static MultiNetRequest newInstance(NetRequest netRequest) {
        return new MultiNetRequest(netRequest);
    }

    public MultiNetRequest(NetRequest netRequest) {
        mNetRequests.add(netRequest);
    }
}
