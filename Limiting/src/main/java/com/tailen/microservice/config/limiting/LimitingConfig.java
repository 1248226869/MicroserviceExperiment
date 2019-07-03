package com.tailen.microservice.config.limiting;

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

    Map<String, String> sf = new ConcurrentHashMap<String, String>();
    private final Logger log = LoggerFactory.getLogger(LimitingConfig.class);

    @Pointcut("@annotation(com.tailen.microservice.config.limiting.Limiting)")
    public void Limiting() {
    }


    @Around("Limiting()")
    public Object LimitingHandle(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getMethod().getParameterTypes());
        Limiting annotation = method.getAnnotation(Limiting.class);
        //获取注解参数
        int frequency = annotation.frequency();
        LimitingType limitingType = annotation.limitingType();
        TimeUnit timeUnit = annotation.timeUnit();

        log.info("开始执行{}#{}方法，入参为{}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), joinPoint.getArgs());
        Object result = joinPoint.proceed();
        log.info("执行{}#{}方法结束，入参为{},结果为{}", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName(), joinPoint.getArgs(), result);
        //通过
        return result;
    }

    private boolean tokenBucket() {

        return false;
    }


}
