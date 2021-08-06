package com.cxytiandi.sharding.config.test;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/7/19
 * @Version 1.0.0
 */

import java.sql.*;

public class SelfMysql {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try {
            String sql="select * from user_0 where id>0";
            //1,注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2,获取连接
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/ds_0?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC", "root", "ztl@ai1314");
            //3,获取语句会话对象,这里我们获取的是预编译处理会话，可以防止SQL注入
            ps=conn.prepareStatement(sql);
            //4,执行语句得到返回值
            rs=ps.executeQuery();
            while (rs.next()) {

                System.out.print(rs.getLong("id") + ",");
                System.out.print(rs.getString("name") + ",");
                System.out.print(rs.getInt("flag") + ",");
                System.out.println(rs.getString("city"));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
