package com.cxytiandi.sharding.config.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/3
 * @Version 1.0.0
 */
@Component
public class GuavaCache {
    private ConcurrentHashMap<String, Cache<String, Object>> cachePool=new ConcurrentHashMap<String, Cache<String, Object>>(50);

    public void setCacheExpireAfterWrite(String topic, Long maxiumSize, Long expireTime, TimeUnit unit, Integer concurrencyLevel) {

        cachePool.putIfAbsent(topic, CacheBuilder.newBuilder().maximumSize(maxiumSize).expireAfterWrite(expireTime, unit).concurrencyLevel(concurrencyLevel).recordStats().build());
    }

    public void setCachseExpireAfterAccess(String topic, Long maxiumSize, Long expireTime, TimeUnit unit, Integer concurrencyLevel) {
        cachePool.putIfAbsent(topic, CacheBuilder.newBuilder().maximumSize(maxiumSize).expireAfterAccess(expireTime, unit).concurrencyLevel(concurrencyLevel).recordStats().build());
    }

    public Object getCache(String topic, String key) {
        return cachePool.get(topic).getIfPresent(key);
    }

    public void putCache(String topic, String key, Object o) {
        cachePool.get(topic).put(key, o);
    }
}
