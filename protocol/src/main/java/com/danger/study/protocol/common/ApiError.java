package com.danger.study.protocol.common;

import java.io.Serializable;

/**
 * Created by PC-361 on 2016/12/20.
 */
public enum ApiError implements IApiError, Serializable {
    SUCCESS(0, "成功"),
    ACTION_ERROR(-10, "误操作"),
    AFFAIR_EXCETION(-100, "通用事务异常"),
    LOCK_ERROR(-101, "获取锁超时失败"),
    UNKNOWN_ERROR(-1000, "未知错误");

    private int code = -1;
    private String msg = "";

    ApiError() {
    }

    ApiError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
