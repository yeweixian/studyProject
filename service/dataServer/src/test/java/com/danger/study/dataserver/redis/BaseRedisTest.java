package com.danger.study.dataserver.redis;

import com.danger.study.dataserver.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

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
//        RedisTemplate template = context.getBean(RedisTemplate.class);
        JedisConnectionFactory jedisConnectionFactory = context.getBean(JedisConnectionFactory.class);
        Jedis jedis = (Jedis) jedisConnectionFactory.getConnection().getNativeConnection();
        jedis.set("test", "test");
        jedis.close();
    }

    public void testRedisTemplate() {
        RedisTemplate<String, String> template = context.getBean(RedisTemplate.class);
        template.opsForValue().set("RedisTemplate", "RedisTemplate");
    }

    @Test
    public void test() {}
}
