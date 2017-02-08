package com.danger.study.dataserver.redis;

import com.danger.study.lock.tools.LockHelper;
import com.danger.study.protocol.common.ApiError;
import com.danger.study.protocol.common.ApiResult;
import com.danger.study.tools.common.JsonUtils;
import com.danger.study.tools.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/2/6.
 */
@Component
public class CountRedis extends AbsRedis {

    @Autowired
    private LockHelper lockHelper;

    public void countByTestId(long testId, long num) {
        try {
            String lockKey = "countByTestId_" + testId;
            boolean l = lockHelper.lock(lockKey, LockHelper.DEFAULT_WAITTIME, LockHelper.DEFAULT_TIMEOUT);
            if (l) {
                long actionBeginTime = System.currentTimeMillis();
                process(jedis -> {
                    String key = "testId_" + testId;
                    String value = jedis.get(key);
                    value = StringUtils.isEmpty(value) ? "0" : value;
                    long sum = Long.valueOf(value) + num;
                    //Thread.sleep(1000 * 2);
                    jedis.set(key, String.valueOf(sum));
                    return null;
                });
                lockHelper.release(lockKey, actionBeginTime, LockHelper.DEFAULT_TIMEOUT);
            } else {
                System.out.println(JsonUtils.getJsonString(new ApiResult<>(ApiError.LOCK_ERROR)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
