package com.danger.study.dataserver.redis;

import com.danger.study.dataserver.helper.RedisHelper;
import com.danger.study.dataserver.redis.intf.IFunction;
import com.danger.study.dataserver.redis.intf.IRollbackFunction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import redis.clients.jedis.Jedis;

/**
 * Created by PC-361 on 2017/2/5.
 */
public abstract class AbsRedis {

    @Autowired
    protected RedisHelper redisHelper;

    protected final <T> T process(IFunction<T> function) {
        return process(function, null);
    }

    protected final <T> T process(IFunction<T> function, IRollbackFunction rollbackFunction) {
        T result = null;
        RedisConnection connection = redisHelper.getConnection();
        Jedis jedis = (Jedis) connection.getNativeConnection();
        try {
            result = function.action(jedis);
        } catch (Exception e) {
            Logger.getLogger(AbsRedis.class).warn(e.toString(), e);
            if (rollbackFunction != null) {
                rollbackFunction.action(e);
            }
        } finally {
            connection.close();
        }
        return result;
    }
}
