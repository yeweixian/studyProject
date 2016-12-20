package com.danger.study.protocol.common;

/**
 * Created by PC-361 on 2016/12/20.
 */
public class ApiException extends Exception {

    private IApiError apiError;

    public ApiException(IApiError apiError) {
        super();
        this.apiError = apiError;
    }

    public ApiException(String message, IApiError apiError) {
        super(message);
        this.apiError = apiError;
    }

    public IApiError getApiError() {
        return apiError;
    }
}
