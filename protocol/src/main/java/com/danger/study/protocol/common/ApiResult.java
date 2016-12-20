package com.danger.study.protocol.common;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * Created by PC-361 on 2016/12/20.
 */
public class ApiResult<T> implements Serializable {

    private int code = -1;
    private String msg = "";
    private T data;

    public ApiResult() {
    }

    public ApiResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ApiResult(IApiError error, T data) {
        this(error.getCode(), error.getMsg(), data);
    }

    public ApiResult(IApiError error) {
        this(error, null);
    }

    private ApiResult(String postfixMsg, IApiError error, T data) {
        this(error, data);
        Logger.getLogger(ApiResult.class).warn("" + error.getCode() + "||" + error.getMsg() + "||" + postfixMsg);
    }

    public ApiResult(String postfixMsg, IApiError error) {
        this(postfixMsg, error, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
