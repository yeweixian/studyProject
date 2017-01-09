package com.danger.study.dataserver.affair;

import com.danger.study.dataserver.dao.CountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/1/9.
 */
@Component
public class CountAffair extends AbsAffair {

    @Autowired
    private CountDao countDao;

    public void addCountByTestId(long testId, long num) {
        process(session -> {
            countDao._addCountByTestId(session, testId, num);
            return null;
        });
    }

    public void subCountByTestId(long testId, long num) {
        process(session -> {
            countDao._subCountByTestId(session, testId, num);
            return null;
        });
    }
}
