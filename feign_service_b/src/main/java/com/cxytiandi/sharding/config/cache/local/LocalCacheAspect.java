package com.cxytiandi.sharding.config.cache.local;

import com.cxytiandi.sharding.config.cache.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */

@Component
@Aspect
public class LocalCacheAspect {
    @Autowired
    private LocalCachePool localCachePool;

    @Around("@annotation(com.cxytiandi.sharding.config.cache.local.LocalCache)")
    public Object handleLocalCache(JoinPoint p) throws IllegalAccessException {

        MethodSignature methodSignature=((MethodSignature) p.getSignature());
        Method method=methodSignature.getMethod();

        LocalCache localCache=method.getAnnotation(LocalCache.class);

        String topic=localCache.topic();
        if (topic == null) {
            throw new ParamException("topic is null");
        }

        localCachePool.setCacheExpireAfterWrite(topic,1000L,60L, TimeUnit.SECONDS,1000);

        //获得注解所在方法的参数名称列表
        String[] parameterNameList=methodSignature.getParameterNames();
        //获得注解所在方法的参数值列表
        Object[] paramList=p.getArgs();
        //参数注解，1维是参数，2维是注解
        Annotation[][] annotationParamList=method.getParameterAnnotations();
        String majorKey="";
        List<String> minorKeyList=new ArrayList<>();
        Object cacheObject=null;

        if (localCache.updateMode() == CacheUpdateMode.executeAfterWrite) {
            //先读取缓存，若存在则返回，若不存在则执行后加缓存：select'sql
            String majork=localCache.majorkey();
            if (majork != null){
                Object byMajorKey=getByMajorKey(topic, majork);
                if (byMajorKey != null) return byMajorKey;
            }
            String minorK=localCache.minorKey();
            Object byMinorKey=getByMinorKey(topic, minorK);
            if (byMinorKey != null) return byMinorKey;


            Object run=run((ProceedingJoinPoint) p);
            Field[] fields=run.getClass().getDeclaredFields();
            for (Field f:fields){
                ObjFieldCache objFieldCache=f.getAnnotation(ObjFieldCache.class);
                if (objFieldCache == null){
                    continue;
                }
                if (!objFieldCache.isKey()){
                    continue;
                }
                f.setAccessible(true);
                if (objFieldCache.isMajorKey()){
                    majorKey =String.valueOf(f.get(run));
                }
                minorKeyList.add(String.valueOf(f.get(run)));
            }
            localCachePool.puts(topic, minorKeyList, majorKey, cacheObject);
            return run;
        } else if (localCache.updateMode() == CacheUpdateMode.executeBeforeWrite) {

            //先写缓存后执行:insert,update,delete'sql

            for (int i=0; i < paramList.length; i++) {
                Object param =paramList[i];
                String parameterName = parameterNameList[i];
                Annotation[] annotationList = annotationParamList[i];
                for (Annotation annotation:annotationList){
                    if (annotation.annotationType().equals(MethodCache.class)) {

                        MethodCache methodCache=(MethodCache)annotation;
                        if (methodCache.valueContainKey()){
                            Field[] fields=param.getClass().getDeclaredFields();
                            for (Field f:fields){
                                ObjFieldCache objFieldCache=f.getAnnotation(ObjFieldCache.class);
                                if (objFieldCache == null){
                                    continue;
                                }
                                if (!objFieldCache.isKey()){
                                    continue;
                                }
                                f.setAccessible(true);
                                if (objFieldCache.isMajorKey()){
                                    majorKey =String.valueOf(f.get(param));
                                }
                                minorKeyList.add(String.valueOf(f.get(param)));
                            }
                            break;
                        }

                        if (methodCache.isCacheValue()){
                            cacheObject = param;
                            continue;
                        }

                        if (methodCache.isMajor()){
                            majorKey =String.valueOf(param);
                        }else {
                            minorKeyList.add(parameterName);
                        }
                    }
                }
            }

            localCachePool.puts(topic, minorKeyList, majorKey, cacheObject);

            run((ProceedingJoinPoint) p);
        }

        return null;
    }

    private Object getByMinorKey(String topic, String minorK) {
        if ( minorK != null){
            Object byMinorKey=localCachePool.getByMinorKey(topic, minorK);
            if(byMinorKey != null){
                return byMinorKey;
            }
        }
        return null;
    }

    private Object getByMajorKey(String topic, String majork) {
        Object byMajorKey=localCachePool.getByMajorKey(topic, majork);
        if (byMajorKey != null){
            return byMajorKey;
        }
        return null;
    }

    private Object run(ProceedingJoinPoint p) {
        try {
            return p.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }
}
