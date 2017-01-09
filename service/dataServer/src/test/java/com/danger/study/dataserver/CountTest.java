package com.danger.study.dataserver;

import com.danger.study.dataserver.affair.CountAffair;
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
            Runnable r1 = () -> {
                try {
                    Thread.sleep((new Random()).nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countAffair.addCountByTestId(1, 2);
                count.add(1);
            };
            r1.run();
            Runnable r2 = () -> {
                try {
                    Thread.sleep((new Random()).nextInt(10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countAffair.subCountByTestId(1, 1);
                count.add(-1);
            };
            r2.run();
        }
        while (count.size() < 100) {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(JsonUtils.getJsonString(count));
    }

    @Test
    public void test() {}
}
