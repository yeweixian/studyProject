package com.danger.study.lock.tools;

import com.danger.study.lock.intf.IFunction;
import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
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
        jedisConnectionFactory.setHostName("192.168.20.58");
        jedisConnectionFactory.setPassword("Redis!@#123");
        jedisConnectionFactory.setPort(6379);
        jedisConnectionFactory.setTimeout(2000);
        jedisConnectionFactory.afterPropertiesSet();
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

    public final <T> T process(IFunction<T> function) {
        T result = null;
        RedisConnection connection = getConnection();
        Jedis jedis = (Jedis) connection.getNativeConnection();
        try {
            result = function.action(jedis);
        } catch (Exception e) {
            Logger.getLogger(LockRedisHelper.class).warn(e.toString(), e);
        } finally {
            connection.close();
        }
        return result;
    }
}
