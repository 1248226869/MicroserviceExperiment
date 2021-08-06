package com.cxytiandi.sharding.config.cache.local;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
@Component
public class LocalCachePool {
    private ConcurrentHashMap<String/* topic */, LocalCacheData> cachePool=new ConcurrentHashMap<String, LocalCacheData>(50);

    public void setCacheExpireAfterWrite(String topic, Long maxiumSize, Long expireTime, TimeUnit unit, Integer concurrencyLevel) {
        if (cachePool.containsKey(topic)) {
            return;
        }
        cachePool.putIfAbsent(topic, new LocalCacheData(ExpireMode.writ, maxiumSize, expireTime, unit, concurrencyLevel));
    }

    public void setCacheExpireAfterAccess(String topic, Long maxiumSize, Long expireTime, TimeUnit unit, Integer concurrencyLevel) {
        if (cachePool.containsKey(topic)) {
            return;
        }
        cachePool.putIfAbsent(topic, new LocalCacheData(ExpireMode.access, maxiumSize, expireTime, unit, concurrencyLevel));
    }

    public boolean puts(String topic, List<String> minorKeyList, String majorKey, Object v) {
        return cachePool.get(topic).puts(minorKeyList, majorKey, v);
    }

    public void updateByMinorKey(String topic, String k, Object v) {
        cachePool.get(topic).updateByMinorKey(k, v);
    }

    public void updateByMajorKey(String topic, String k, Object v) {
        cachePool.get(topic).updateByMajorKey(k, v);
    }

    public Object getByMinorKey(String topic, String k) {
        return cachePool.get(topic).getByMinorKey(k);
    }

    public Object getByMajorKey(String topic, String k) {
        return cachePool.get(topic).getByMajorKey(k);
    }

}
