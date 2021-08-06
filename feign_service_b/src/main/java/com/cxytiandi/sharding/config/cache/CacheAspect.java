package com.cxytiandi.sharding.config.cache;

import com.cxytiandi.sharding.config.moreDadtaSource.DataSourceContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */

@Component
@Aspect
public class CacheAspect {
    @Autowired
    private GuavaCache guavaCache;

    @Around("@annotation(com.cxytiandi.sharding.config.cache.TwoWayCache)")
    public void handleTwoWayCache(JoinPoint p) {
        MethodSignature methodSignature=((MethodSignature) p.getSignature());
        Method method=methodSignature.getMethod();
        TwoWayCache dbSource=method.getAnnotation(TwoWayCache.class);
        dbSource.topic();
        //获得注解所在方法的参数名称列表
        String[] parameterNames=methodSignature.getParameterNames();
        //获得注解所在方法的参数值列表
        Object[] params=p.getArgs();
        //参数注解，1维是参数，2维是注解
        Annotation[][] annotations=method.getParameterAnnotations();
        for (int i=0; i < annotations.length; i++) {
            Object param=params[i];
            if (param instanceof ObjField){
                System.out.println("处理对象上的");

            }else {
                String parameterName=parameterNames[i];
                Annotation[] paramAnn=annotations[i];
                if (param == null || paramAnn.length == 0) {
                    continue;
                }
                for (Annotation annotation : paramAnn) {
                    //这里判断当前注解是否为Test.class
                    if (annotation.annotationType().equals(FieldCache.class)) {
                        //校验该参数，验证一次退出该注解
                        System.out.println(param.toString());
                        //TODO 校验参数
                        break;
                    }
                }
            }

        }

        //处理缓存
        try {
            //缓存失效
            ((ProceedingJoinPoint) p).proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
        }
    }
}
