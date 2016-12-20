package com.danger.study.dataserver.affair.intf;

import org.apache.ibatis.session.SqlSession;

/**
 * Created by PC-361 on 2016/12/20.
 */
public interface IAffairFunction<T> {
    T action(SqlSession session) throws Exception;
}
