package com.danger.study.protocol.data.affair;

import com.danger.study.protocol.common.ApiResult;
import com.danger.study.protocol.data.entity.User;

import java.util.List;

/**
 * Created by PC-361 on 2016/12/20.
 */
public interface IUserAffair {
    ApiResult<List<User>> findAllUser();
}
