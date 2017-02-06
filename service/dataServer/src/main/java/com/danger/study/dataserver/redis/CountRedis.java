package com.danger.study.dataserver.redis;

import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/2/6.
 */
@Component
public class CountRedis extends AbsRedis {

    public void countByTestId(long testId, long num) {
        process(jedis -> {
            String key = "testId_" + testId;
            String value = jedis.get(key);
            long sum = Long.valueOf(value) + num;
            jedis.set(key, String.valueOf(sum));
            return null;
        });
    }
}
