package com.danger.study.dataserver.mapper;

import com.danger.study.protocol.data.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by PC-361 on 2016/11/18.
 */
@Mapper
public interface UserMapper {

    @Select("select * from user")
    List<User> findAllUser();
}
