package com.danger.study.dubbo.tools;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2016/10/18.
 */
@Component
public class RpcHelper {

    @Autowired
    private ApplicationContext context;

    public <T> T getRemoteBean(Class<T> t) {
        T obj = null;
        try {
            obj = context.getBean(t);
        } catch (Exception e) {
            Logger.getLogger(RpcHelper.class).warn(e.toString(), e);
        }
        return obj;
    }
}
