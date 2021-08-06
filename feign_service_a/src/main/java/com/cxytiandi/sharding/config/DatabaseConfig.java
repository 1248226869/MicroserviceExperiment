package com.cxytiandi.sharding.config;

import org.springframework.beans.factory.annotation.Value;

import javax.sql.DataSource;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/14
 * @Version 1.0.0
 */
//@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource1.type}")
    private Class<? extends DataSource> dataSourceType;
//    /**
//     *  获取配置文件数据源datasource0
//     */
//    @Bean(name="dataSource")
//    @Primary
//    @ConfigurationProperties(prefix="spring.datasource1")
//    public DataSource getDataSource() {
//        DataSource build=DataSourceBuilder.create().type(dataSourceType).build();
//        return build;
//    }


}
