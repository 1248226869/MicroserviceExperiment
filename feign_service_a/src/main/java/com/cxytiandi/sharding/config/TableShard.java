package com.cxytiandi.sharding.config;

import java.lang.annotation.*;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/19
 * @Version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableShard {

    String tableName();

    String field();

    // 对应的分表策略类
    Class<? extends ITableShardStrategy> shardStrategy();

}
