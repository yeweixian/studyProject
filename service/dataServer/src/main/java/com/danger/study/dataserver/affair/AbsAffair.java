package com.danger.study.dataserver.affair;

import com.danger.study.dataserver.affair.intf.IAffairFunction;
import com.danger.study.dataserver.affair.intf.IRollbackFunction;
import com.danger.study.dataserver.helper.MybatisHelper;
import com.danger.study.protocol.common.ApiError;
import com.danger.study.protocol.common.ApiException;
import com.danger.study.protocol.common.ApiResult;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by PC-361 on 2016/12/20.
 */
public abstract class AbsAffair {

    @Autowired
    protected MybatisHelper mybatisHelper;

    protected final <T> ApiResult<T> process(IAffairFunction<T> affairFunction) {
        return process(affairFunction, null);
    }

    protected final <T> ApiResult<T> process(IAffairFunction<T> affairFunction, IRollbackFunction rollbackFunction) {
        ApiResult<T> result = new ApiResult<>();
        SqlSession session = mybatisHelper.openSession();
        try {
            T data = affairFunction.action(session);
            session.commit();
            result = new ApiResult<>(ApiError.SUCCESS, data);
        } catch (Exception e) {
            Logger.getLogger(AbsAffair.class).warn(e.toString(), e);
            if (e instanceof ApiException) {
                result = new ApiResult<>(e.getMessage(), ((ApiException) e).getApiError());
            } else {
                result = new ApiResult<>(e.getMessage(), ApiError.AFFAIR_EXCETION);
            }
            session.rollback();
            if (rollbackFunction != null) {
                rollbackFunction.action(e);
            }
        } finally {
            session.close();
        }
        return result;
    }
}
