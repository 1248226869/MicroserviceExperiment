package com.cxytiandi.sharding.config.cache;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
@Target(PARAMETER)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldCache {
}
