package com.orange.ndlib.response;

import com.orange.lib.common.reponse.PullData;

import java.util.ArrayList;
import java.util.List;

public class PullDemoData extends ArrayList<String> implements PullData<String> {

    /**
     * DATA转List<ITEM>
     *
     * @return
     */
    @Override
    public List<String> getList() {
        return this;
    }
}