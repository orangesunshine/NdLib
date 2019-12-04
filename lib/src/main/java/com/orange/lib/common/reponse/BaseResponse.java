package com.orange.lib.common.reponse;

import static com.orange.lib.constance.IConst.CODE_ERROR;

public class BaseResponse<Data> {
    public int code;
    public String msg;
    public Data data;

    public BaseResponse() {
        this.code = CODE_ERROR;
    }

    public BaseResponse(String msg) {
        this.msg = msg;
    }
}
