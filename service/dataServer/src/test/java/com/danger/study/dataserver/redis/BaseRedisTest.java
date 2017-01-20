package com.danger.study.dataserver.redis;

import com.danger.study.dataserver.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by PC-361 on 2017/1/20.
 */
@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BaseRedisTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testRedis() {
        RedisTemplate template = context.getBean(RedisTemplate.class);
    }

    @Test
    public void test() {}
}
