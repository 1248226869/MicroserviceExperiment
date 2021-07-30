package com.cxytiandi.sharding.config;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/19
 * @Version 1.0.0
 */
public class DateTableShardStrategy implements ITableShardStrategy {


    @Override
    public String tableShard(String tableName,Long field) {

        return new StringBuilder(tableName).append("_").append(field%3).toString();
    }

}
