package com.cxytiandi.sharding.config.cache.local;

import com.cxytiandi.sharding.config.cache.CacheUpdateMode;

import java.lang.annotation.*;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface LocalCache {

    String topic();

    CacheUpdateMode updateMode();

    String majorkey();

    String minorKey();
}
