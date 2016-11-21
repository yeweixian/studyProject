package com.danger.study.dataserver.helper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by PC-361 on 2016/11/21.
 */
@Component
public class MybatisHelper {

    private SqlSessionFactory sqlSessionFactory;

    @PostConstruct
    private void postConstruct() {
        init("mybatis-config.xml");
    }

    public boolean init(String resource) {
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(MybatisHelper.class).warn(e.getMessage(), e);
            return false;
        }
        return true;
    }

    public SqlSession openSession() {
        if (sqlSessionFactory != null){
            return sqlSessionFactory.openSession();
        }
        return null;
    }

    public Transaction getTransaction(SqlSession session){
        return new JdbcTransactionFactory().newTransaction(session.getConnection());
    }
}
