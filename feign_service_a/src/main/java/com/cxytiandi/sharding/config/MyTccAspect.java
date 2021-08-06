package com.cxytiandi.sharding.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/15
 * @Version 1.0.0
 */
@Aspect
@Component
@SuppressWarnings({"unused"})
public class MyTccAspect {
    public static final Logger logger=LoggerFactory.getLogger(MyTccAspect.class);

    @Pointcut("@annotation( com.cxytiandi.sharding.config.MyTcc)")
    public void annotationPointcut() {
    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) {
    }

    @Around("annotationPointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature=(MethodSignature) joinPoint.getSignature();
        String[] params=methodSignature.getParameterNames();// 获取参数名称
        Object[] args=joinPoint.getArgs();// 获取参数值
        for (String p : params) {
            System.out.println("param is " + p);
        }
        for (Object p : args) {
            System.out.println("args is " + p.toString());
        }
        return joinPoint.proceed();
    }


    @AfterReturning("annotationPointcut()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }


}

