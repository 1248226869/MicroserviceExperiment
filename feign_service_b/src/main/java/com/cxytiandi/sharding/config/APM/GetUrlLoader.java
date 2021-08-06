package com.cxytiandi.sharding.config.APM;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/2
 * @Version 1.0.0
 */

import com.cxytiandi.sharding.FeignServiceBApplication;
import com.cxytiandi.sharding.config.cache.GuavaCache;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Map;

@Component
public class GetUrlLoader implements ApplicationContextAware {
    private final Logger log=LoggerFactory.getLogger(FeignServiceBApplication.class);



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        sout(applicationContext, applicationContext.getBeansWithAnnotation(RestController.class));
        sout(applicationContext, applicationContext.getBeansWithAnnotation(Controller.class));
        GuavaCache cacheA=applicationContext.getBean("cacheA", GuavaCache.class);
        log.info(" cacheA is {}",cacheA.toString());

    }

    private void sout(ApplicationContext applicationContext, Map<String, Object> beans) {
        for (String beanName : beans.keySet()) {
            Object value = applicationContext.getBean(beanName);
            if (value == null) {
                continue;
            }
            RequestMapping requestMapping = AnnotationUtils.findAnnotation(value.getClass(), RequestMapping.class);
            if (requestMapping == null) {
                continue;
            }
            String path = requestMapping.value()[0];
            Method[] methods = value.getClass().getMethods();
            for (Method method : methods) {
                //每个方法必定含有下面的注解中的其中一个
                ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
                String url = "";
                String desc = "";
                if (apiOperation != null) {
                    desc = apiOperation.value();
                }
                RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                PostMapping postMapping = AnnotationUtils.findAnnotation(method, PostMapping.class);
                GetMapping getMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);
                PutMapping putMapping = AnnotationUtils.findAnnotation(method, PutMapping.class);
                DeleteMapping deleteMapping = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
                if (postMapping != null&& postMapping.value().length>0) {
                    url = path + postMapping.value()[0];
                } else if (getMapping != null&& getMapping.value().length>0) {
                    url = path + getMapping.value()[0];
                } else if (putMapping != null&& putMapping.value().length>0) {
                    url = path + putMapping.value()[0];
                } else if (deleteMapping != null&& deleteMapping.value().length>0) {
                    url = path + deleteMapping.value()[0];
                } else if (mapping != null && mapping.value().length>0) {
                    //mapping 顺序一定要在后面
                    url = path + mapping.value()[0];
                } else {
                    continue;
                }
                log.info("url : {}  , desc : {}", url, desc);
            }

        }
    }
}
