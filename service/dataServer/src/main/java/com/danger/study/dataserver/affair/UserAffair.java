package com.danger.study.dataserver.affair;

import com.danger.study.dataserver.dao.UserDao;
import com.danger.study.protocol.common.ApiResult;
import com.danger.study.protocol.data.affair.IUserAffair;
import com.danger.study.protocol.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by PC-361 on 2016/12/20.
 */
public class UserAffair extends AbsAffair implements IUserAffair {

    @Autowired
    private UserDao userDao;

    @Override
    public ApiResult<List<User>> findAllUser() {
        return process(session -> userDao.findAllUser(session));
    }
}
