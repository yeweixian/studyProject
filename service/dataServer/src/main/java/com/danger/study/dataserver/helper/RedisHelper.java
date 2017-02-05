package com.danger.study.dataserver.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/1/20.
 */
@Component
public class RedisHelper {

    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;

    public RedisConnection getConnection() {
        return jedisConnectionFactory.getConnection();
    }
}
