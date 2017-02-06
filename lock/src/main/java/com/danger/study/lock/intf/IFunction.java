package com.danger.study.lock.intf;

import redis.clients.jedis.Jedis;

/**
 * Created by PC-361 on 2017/2/5.
 */
public interface IFunction<T> {
    T action(Jedis jedis) throws Exception;
}
