package com.danger.study.dataserver.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by PC-361 on 2017/4/5.
 */
@Aspect
@Component
public class TestAspect {

    @Pointcut("execution(public * com.danger.study.dataserver..*.*(..))")
    public void test() {}

    @Before("test()")
    public void doBefore() {
        System.out.println(" ------ AOP doBefore function!");
    }

    @AfterReturning(returning = "ret", pointcut = "test()")
    public void doAfterReturning(Object ret) {
        System.out.println(" ------ AOP doAfterReturning: " + ret);
    }
}
