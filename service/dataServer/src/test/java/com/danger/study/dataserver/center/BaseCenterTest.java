package com.danger.study.dataserver.center;

import com.danger.study.dataserver.Application;
import com.danger.study.dataserver.dao.UserDao;
import com.danger.study.protocol.common.ApiResult;
import com.danger.study.protocol.data.affair.IUserAffair;
import com.danger.study.protocol.data.entity.User;
import com.danger.study.tools.common.JsonUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by PC-361 on 2016/11/18.
 */
@RunWith(SpringRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class BaseCenterTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void testGetBaseMsg() {
        BaseCenter baseCenter = context.getBean(BaseCenter.class);
        System.out.println("--- test msg: " + baseCenter.getBaseMsg());
    }

    @Test
    public void testFindAllUser() {
        UserDao userDao = context.getBean(UserDao.class);
        List<User> userList = userDao.findAllUser();
        System.out.println("----------------------------");
        for (User user : userList) {
            System.out.println(JsonUtils.getJsonString(user));
        }
        System.out.println("----------------------------");
    }

    @Test
    public void testFindAllUserByAffair() {
        IUserAffair userAffair = context.getBean(IUserAffair.class);
        ApiResult<List<User>> result = userAffair.findAllUser();
        System.out.println("----------------------------");
        System.out.println(JsonUtils.getJsonString(result));
        System.out.println("----------------------------");
    }

    @Test
    public void test() {}
}
