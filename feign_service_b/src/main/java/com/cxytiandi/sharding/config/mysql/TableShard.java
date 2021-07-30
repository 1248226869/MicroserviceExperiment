package com.cxytiandi.sharding.config.mysql;

import java.lang.annotation.*;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/19
 * @Version 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableShard {

    // 要替换的表名
    String tableName();


    //分表策略字段
    String fieldShard();

    // 对应的分表策略类
    Class<? extends ITableShardStrategy> shardStrategy();

}
