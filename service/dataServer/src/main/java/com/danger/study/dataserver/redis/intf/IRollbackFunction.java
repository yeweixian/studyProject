package com.danger.study.dataserver.redis.intf;

/**
 * Created by PC-361 on 2016/12/20.
 */
public interface IRollbackFunction {
    void action(Exception exception);
}
