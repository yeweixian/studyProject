package com.danger.study.dataserver.dao;

import com.danger.study.dataserver.mapper.CountMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/1/9.
 */
@Component
public class CountDao {

    public void _addCountByTestId(SqlSession session, long testId, long num) {
        CountMapper countMapper = session.getMapper(CountMapper.class);
        countMapper.addCountByTestId(testId, num);
    }

    public void _subCountByTestId(SqlSession session, long testId, long num) {
        CountMapper countMapper = session.getMapper(CountMapper.class);
        countMapper.subCountByTestId(testId, num);
    }
}
