package com.cxytiandi.sharding.config.limiting;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhao tailen
 * @description: 请求权限、参数与返回校验；异常统一处理;限流
 * @date 2019-07-02
 */
@Aspect
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LimitingConfig {
    private final Logger log = LoggerFactory.getLogger(LimitingConfig.class);
    Map<String, ConcurrentHashMap<String, Integer>> signaStoragel =
            new ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>>();

    @Pointcut("@annotation(com.cxytiandi.sharding.config.limiting.Limiting)")
    public void Limiting() {
    }


    @Around("Limiting()")
    public Object LimitingHandle(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        Limiting annotation = method.getAnnotation(Limiting.class);
        //获取注解参数
        int frequency = annotation.frequency();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        log.info("开始执行{}#{}方法，入参为{}", className, methodName, joinPoint.getArgs());
        String key = new StringBuffer().append(className).append("#").append(methodName).toString();
        ConcurrentHashMap<String, Integer> signa = signaStoragel.get(key);
        if (signa == null){
            signa = new ConcurrentHashMap<String, Integer>();
        }

        if (signa.size() < frequency) {
            //租借信号
            String concurrentTime = (new Date()).toString();
            signa.put(concurrentTime,0);
            signaStoragel.put(key, signa);
            //执行方法
            Object result = joinPoint.proceed();
            //归还信号
            signa.remove(concurrentTime);
            signaStoragel.put(key, signa);

            log.info("执行{}#{}方法结束，入参为{},结果为{}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), joinPoint.getArgs(), result);
            return result;
        }
        log.info("暂时无权执行{}#{}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
        //重试
        return null;
    }


}
