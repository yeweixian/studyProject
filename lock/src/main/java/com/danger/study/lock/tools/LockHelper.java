package com.danger.study.lock.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/2/7.
 */
@Component
public class LockHelper {

    public static final int DEFAULT_WAITTIME = 4000;
    public static final int DEFAULT_TIMEOUT = 2000;

    @Autowired
    private LockRedisHelper lockRedisHelper;

    public boolean lock(String lockKey, long waitTime, long timeout) throws InterruptedException {
        long beginTime = System.currentTimeMillis();
        //使用 setnx 判断是否 获得锁
        Long result = lockRedisHelper.process(jedis -> jedis.setnx(lockKey, String.valueOf(System.currentTimeMillis() + timeout)));
        if (result == 1) return true;
        while (true) {
            //超时返回 false 表示 没有获取 锁
            if (beginTime + waitTime < System.currentTimeMillis()) return false;
            //使用 get 获取 上次锁的 销毁时间
            long oldExpireTime = Long.valueOf(lockRedisHelper.process(jedis -> jedis.get(lockKey)));
            if (oldExpireTime < System.currentTimeMillis()) {
                //使用 getset 添加 新锁的 销毁时间
                String timeStr = lockRedisHelper.process(jedis -> jedis.getSet(lockKey, String.valueOf(System.currentTimeMillis() + timeout)));
                long currentExpireTime = Long.valueOf(timeStr);
                if (currentExpireTime == oldExpireTime) return true;
            }
            Thread.sleep(100);
        }
    }

    public void release(String lockKey, long actionBeginTime, long timeout) {
        if (System.currentTimeMillis() < actionBeginTime + timeout) {
            lockRedisHelper.process(jedis -> jedis.getSet(lockKey, String.valueOf(System.currentTimeMillis())));
        }
    }
}
