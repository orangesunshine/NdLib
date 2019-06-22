package com.orange.lib.common.reponse;

public class BaseResponse<DATA> {
    public int code;
    public String msg;
    public DATA data;
}
