package com.orange.lib.common.reponse;


import com.orange.lib.constance.IConst;
import com.orange.lib.utils.base.Preconditions;

import java.util.List;

public interface PullData<ITEM> {

    /**
     * DATA转List<ITEM>
     *
     * @return
     */
    List<ITEM> getList();

    /**
     * 没有更多数据
     *
     * @return
     */
    default boolean noMoreData() {
        boolean noMoreData = true;
        List<ITEM> list = getList();
        if (Preconditions.isEmpty(list)) return noMoreData;
        if (list.size() > IConst.PAGE_SIZE)
            noMoreData = false;
        return noMoreData;
    }

    /**
     * 没有更多数据
     *
     * @return
     */
    default boolean empty() {
        return Preconditions.isEmpty(getList());
    }
}
