package com.cxytiandi.sharding.config.moreDadtaSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description 该类是为了使用自定义注解@DataSource使用的，
 *              在@DataSource标记的方法前切换为@DataSource参数指定数据源，
 *              方法后清除DataSourceContextHolder中的数据源信息，以免影响默认的数据源。
 * @Author zhao tailin
 * @Date 2020/7/22
 * @Version 1.0.0
 */
@Component
@Aspect
public class DynamicDataSourceAspect {
    @Around("@annotation(com.cxytiandi.sharding.config.moreDadtaSource.DBSource)")
    public void switchDS(JoinPoint p) {
        //获得注解所在方法签名
        Method m=((MethodSignature)p.getSignature()).getMethod();
        DBSource dbSource = m.getAnnotation(DBSource.class);
        DataSourceType datasource=dbSource.value();
        DataSourceContextHolder.setDataSource(datasource);//修改所用数据源为注解参数
        try {
            ((ProceedingJoinPoint)p).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }finally {
            DataSourceContextHolder.clearDataSource();//清除数据源信息
        }
    }
}

