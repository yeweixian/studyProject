package com.danger.study.dataserver;

import com.danger.study.dataserver.affair.CountAffair;
import com.danger.study.dataserver.redis.CountRedis;
import com.danger.study.tools.common.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PC-361 on 2017/1/9.
 */
@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CountTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testCount() {
        List<Integer> count = new ArrayList<>();
        CountAffair countAffair = context.getBean(CountAffair.class);
        for (int i = 0; i < 50; i ++) {
            Thread t1 = new Thread(() -> {
                try {
                    Thread.sleep((new Random()).nextInt(4 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countAffair.addCountByTestId(1, 2);
                count.add(1);
            });
            Thread t2 = new Thread(() -> {
                try {
                    Thread.sleep((new Random()).nextInt(4 * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countAffair.subCountByTestId(1, 4);
                count.add(-1);
            });
            //执行
            t1.start();
            t2.start();
        }
        while (count.size() < 100) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(count.size() + " --- " + JsonUtils.getJsonString(count));
    }

    @Test
    public void testChangeCount() {
        List<Integer> count = new ArrayList<>();
        CountAffair countAffair = context.getBean(CountAffair.class);
        for (int i = 0; i < 100; i ++) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countAffair.countByTestId(1, 2);
                count.add(1);
            });
            //执行
            t.start();
        }
        while (count.size() < 100) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(count.size() + " --- " + JsonUtils.getJsonString(count));
    }

    @Test
    public void testCountInRedis() {
        List<Integer> end = new ArrayList<>();
        List<Integer> begin = new ArrayList<>();
        CountRedis countRedis = context.getBean(CountRedis.class);
        for (int i = 0; i < 100; i ++) {
            final int tn = i;
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep((new Random()).nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                begin.add(tn);
                countRedis.countByTestId(1, 2);
                end.add(tn);
            });
            //执行
            t.start();
        }
        while (end.size() < 100) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(begin.size() + " b--- " + JsonUtils.getJsonString(begin));
        System.out.println(end.size() + " e--- " + JsonUtils.getJsonString(end));
    }

    @Test
    public void test() {}
}
