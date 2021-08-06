package com.cxytiandi.sharding.config.mysql;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/19
 * @Version 1.0.0
 */
public interface ITableShardStrategy {
    String fieldShard(String tableName);
}
