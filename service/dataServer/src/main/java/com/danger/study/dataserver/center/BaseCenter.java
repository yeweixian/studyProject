package com.danger.study.dataserver.center;

import com.danger.study.protocol.data.center.IBaseCenter;

/**
 * Created by PC-361 on 2016/10/11.
 */
public class BaseCenter implements IBaseCenter {

    @Override
    public String getBaseMsg() {
        return "BaseMsg";
    }
}
