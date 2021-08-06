package com.cxytiandi.sharding.config.cache.local;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
public class LocalCacheData {
    private Cache<String, Object> cacheData;

    private Cache<String, String> keyMap;


    public LocalCacheData(ExpireMode expireMode, Long maxiumSize, Long expireTime, TimeUnit unit, Integer concurrencyLevel) {
        if (expireMode.equals(ExpireMode.access)) {
            cacheData=CacheBuilder.newBuilder().maximumSize(maxiumSize).expireAfterAccess(expireTime, unit).concurrencyLevel(concurrencyLevel).recordStats().build();
            keyMap=CacheBuilder.newBuilder().maximumSize(maxiumSize).expireAfterAccess(expireTime, unit).concurrencyLevel(concurrencyLevel).recordStats().build();
            return;
        }
        cacheData=CacheBuilder.newBuilder().maximumSize(maxiumSize).expireAfterWrite(expireTime, unit).concurrencyLevel(concurrencyLevel).recordStats().build();
        keyMap=CacheBuilder.newBuilder().maximumSize(maxiumSize).expireAfterWrite(expireTime, unit).concurrencyLevel(concurrencyLevel).recordStats().build();

    }

    public boolean puts(List<String> minorKeyList, String majorKey, Object v) {

        if (majorKey == null) {
            throw new NotMajorKeyException("majorKey is null");
        }
        keyMap.put(majorKey, majorKey);

        if (minorKeyList != null) {
            for (String k : minorKeyList) {
                keyMap.put(k, majorKey);
            }
        }
        cacheData.put(majorKey, v);

        return true;
    }

    public void updateByMinorKey(String k, Object v) {
        String s=keyMap.getIfPresent(k);
        if (s == null){
            throw new NotMajorKeyException("majorKey is not exist");
        }
        cacheData.put(k,v);
    }

    public void updateByMajorKey(String k, Object v) {
        if (k == null){
            throw new NotMajorKeyException("majorKey is null");
        }
        cacheData.put(k,v);
    }

    public Object getByMinorKey(String k){
        String s=keyMap.getIfPresent(k);
        if (s == null){
            throw new NotMajorKeyException("majorKey is not exist");
        }

        return cacheData.getIfPresent(s);
    }
    public Object getByMajorKey(String k){

        return cacheData.getIfPresent(k);
    }

}
