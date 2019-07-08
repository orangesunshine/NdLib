package com.orange.lib.common.convert;

import com.orange.lib.common.reponse.PullData;

public interface IPullConvert<ITEM> {
    void convert(PullData<ITEM> pullResponse);
}
