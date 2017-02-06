package com.danger.study.lock.tools;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;

/**
 * Created by PC-361 on 2017/2/6.
 */
@Component
public class LockRedisHelper {

    private JedisConnectionFactory jedisConnectionFactory;

    @PostConstruct
    private void postConstruct() {
        init();
    }

    public boolean init() {
        jedisConnectionFactory = new JedisConnectionFactory(jedisPoolConfig());
        jedisConnectionFactory.setDatabase(1);
        jedisConnectionFactory.setHostName("192.168.83.49");
        jedisConnectionFactory.setPassword("Redis!@#123");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setTimeout(1000);
        return true;
    }

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxWaitMillis(-1);
        config.setMaxIdle(30);
        config.setMinIdle(0);
        return config;
    }

    public RedisConnection getConnection() {
        if (jedisConnectionFactory != null) {
            return jedisConnectionFactory.getConnection();
        }
        return null;
    }
}
