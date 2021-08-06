package com.cxytiandi.sharding.config.cache.local;

import java.lang.annotation.*;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/4
 * @Version 1.0.0
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ObjFieldCache {
    String topic();

    boolean isKey() default false;
    boolean isMajorKey() default false;

}
