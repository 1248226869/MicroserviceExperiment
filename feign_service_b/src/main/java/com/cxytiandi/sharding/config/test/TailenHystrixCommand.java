package com.cxytiandi.sharding.config.test;


import java.lang.annotation.*;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/12
 * @Version 1.0.0
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TailenHystrixCommand {

    /**
     * 默认超时时间
     *
     * @return
     */
    int timeout() default 1000;

    /**
     * 回退方法
     *
     * @return
     */
    String fallback() default "";

}
