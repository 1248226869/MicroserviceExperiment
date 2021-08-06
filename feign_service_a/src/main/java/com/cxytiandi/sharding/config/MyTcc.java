package com.cxytiandi.sharding.config;

import java.lang.annotation.*;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/15
 * @Version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyTcc {
    /**
     * 尝试执行业务
     * • 完成所有业务检查(一致性)
     * • 预留必须业务资源(准隔离性)
     *
     * @return
     */
    String tryMethod() default "";

    /**
     * 确认执行业务
     * • 真正执行业务
     * • 不作任何业务检查
     * • 只使用Try阶段预留的业务资源
     * • Confirm操作要满足幂等性
     *
     * @return
     */
    String confirmMethod() default "";

    /**
     * 取消执行业务
     * • 释放Try阶段预留的业务资源
     * • Cancel操作要满足幂等性
     *
     * @return
     */
    String cancelMethod() default "";
}
