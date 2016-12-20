package com.danger.study.dataserver.affair.intf;

/**
 * Created by PC-361 on 2016/12/20.
 */
public interface IRollbackFunction {
    void action(Exception exception);
}
