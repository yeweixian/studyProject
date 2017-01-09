package com.danger.study.dataserver.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * Created by PC-361 on 2016/11/18.
 */
public interface CountMapper {
    void addCountByTestId(@Param("testId") long testId, @Param("num") long num);
    void subCountByTestId(@Param("testId") long testId, @Param("num") long num);
}
