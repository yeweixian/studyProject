package com.danger.study.protocol.data.entity;

import java.io.Serializable;

/**
 * Created by PC-361 on 2017/1/10.
 */
public class Count implements Serializable {
    private long testId;
    private long count;

    public long getTestId() {
        return testId;
    }

    public void setTestId(long testId) {
        this.testId = testId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
