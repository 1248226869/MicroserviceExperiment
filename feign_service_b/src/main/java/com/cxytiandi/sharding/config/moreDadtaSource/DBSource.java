package com.cxytiandi.sharding.config.moreDadtaSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/22
 * @Version 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBSource {
    DataSourceType value() default DataSourceType.MYSQL;
}
