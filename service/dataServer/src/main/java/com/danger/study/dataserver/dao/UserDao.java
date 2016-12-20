package com.danger.study.dataserver.dao;

import com.danger.study.dataserver.helper.MybatisHelper;
import com.danger.study.dataserver.mapper.UserMapper;
import com.danger.study.protocol.data.entity.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by PC-361 on 2016/11/18.
 */
@Component
public class UserDao {

    @Autowired
    private MybatisHelper mybatisHelper;

    public List<User> findAllUser(SqlSession session) {
        UserMapper userMapper = session.getMapper(UserMapper.class);
        return userMapper.findAllUser();
    }

    public List<User> findAllUser() {
        try (SqlSession session = mybatisHelper.openSession()) {
            return findAllUser(session);
        }
    }
}
