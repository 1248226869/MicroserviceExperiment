package com.cxytiandi.sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/22
 * @Version 1.0.0
 */
public class alibaba {
    public static DruidDataSource dashboardDruidDataSource() throws Exception {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/ds_0?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        druidDataSource.setUsername( "root");
        druidDataSource.setPassword("ztl@ai1314");

        return druidDataSource;
    }

    public static void main(String[] args) throws Exception {
        DruidDataSource druidDataSource=dashboardDruidDataSource();
        DruidPooledConnection conn=druidDataSource.getConnection();
        String sql="select * from user_0 where id>0";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        //4,执行语句得到返回值
        ResultSet resultSet=preparedStatement.executeQuery();
        while (resultSet.next()) {

            System.out.print(resultSet.getLong("id") + ",");
            System.out.print(resultSet.getString("name") + ",");
            System.out.print(resultSet.getInt("flag") + ",");
            System.out.println(resultSet.getString("city"));
        }
    }
}
