package com.cxytiandi.sharding.config.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/3
 * @Version 1.0.0
 */
@Component
public class BeanFactoryForCache implements BeanFactoryPostProcessor {

    //获取缓存配置

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //实例化bean
        GuavaCache cache = new GuavaCache();
//        cache.setCacheExpireAfterWrite(1000L,1000L, TimeUnit.SECONDS,1000);
        //注册单例bean
        beanFactory.registerSingleton("cacheA", cache);
        //持久化缓存关系
    }


}
