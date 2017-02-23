package com.danger.study.frontServer.web;

import com.danger.study.dubbo.tools.RpcHelper;
import com.danger.study.protocol.common.ApiError;
import com.danger.study.protocol.common.ApiResult;
import com.danger.study.protocol.data.center.IBaseCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by PC-361 on 2016/10/9.
 */
@RestController
public class Controller {

    @Autowired
    private RpcHelper rpcHelper;

    @RequestMapping("/")
    public String index() {
        IBaseCenter center = rpcHelper.getRemoteBean(IBaseCenter.class);
        return "Hello World!" + center.getBaseMsg();
    }

    public ApiResult<String> getIndexMsg(String msg) {
        try {
            IBaseCenter center = rpcHelper.getRemoteBean(IBaseCenter.class);
            return new ApiResult<>(ApiError.SUCCESS, center.getHelloMsg(msg));
        } catch (Exception e) {
            return new ApiResult<>(e.getMessage(), ApiError.UNKNOWN_ERROR);
        }
    }
}
