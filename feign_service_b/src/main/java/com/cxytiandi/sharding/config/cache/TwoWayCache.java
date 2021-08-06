package com.cxytiandi.sharding.config.cache;

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
public  @interface TwoWayCache {
    String topic() ;
    String[] keys();
}
