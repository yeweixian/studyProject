package com.danger.study.dataserver.center;

import com.danger.study.dataserver.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by PC-361 on 2016/11/18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class BaseCenterTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testGetBaseMsg() {
        BaseCenter baseCenter = context.getBean(BaseCenter.class);
        System.out.println("--- test msg: " + baseCenter.getBaseMsg());
    }
}