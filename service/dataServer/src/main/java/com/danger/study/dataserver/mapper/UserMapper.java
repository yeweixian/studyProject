package com.danger.study.dataserver.mapper;

import com.danger.study.protocol.data.entity.User;

import java.util.List;

/**
 * Created by PC-361 on 2016/11/18.
 */
public interface UserMapper {
    List<User> findAllUser();
}
