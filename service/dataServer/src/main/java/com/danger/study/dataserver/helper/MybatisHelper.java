package com.danger.study.dataserver.helper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.Transaction;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by PC-361 on 2016/11/21.
 */
@Component
public class MybatisHelper {

    private SqlSessionFactory sqlSessionFactory;

    @Value("${spring.datasource.driver-class-name}")
    private String mybatis_driver;
    @Value("${spring.datasource.url}")
    private String mybatis_url;
    @Value("${spring.datasource.username}")
    private String mybatis_username;
    @Value("${spring.datasource.password}")
    private String mybatis_password;

    @PostConstruct
    private void postConstruct() {
        init("mybatis-config.xml");
    }

    public boolean init(String resource) {
        InputStream inputStream;
        Properties properties = new Properties();
        properties.setProperty("driver",mybatis_driver);
        properties.setProperty("url",mybatis_url);
        properties.setProperty("username",mybatis_username);
        properties.setProperty("password",mybatis_password);
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, properties);
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
