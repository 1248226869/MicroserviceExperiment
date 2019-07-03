package com.tailen.microservice.config.limiting;

import java.lang.annotation.*;

/**
 * @author zhao tailen
 * @description
 * @date 2019-07-02
 */
@Target(value = {ElementType.PARAMETER, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Limiting {

    int frequency();
//    TimeUnit timeUnit();
//    LimitingType limitingType();
}
