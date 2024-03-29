package com.yemage.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtil {

    private static DataSource ds = null;
    static{
        try {
            Properties ppt = new Properties();
            ppt.load(DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds = DruidDataSourceFactory.createDataSource(ppt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 从连接池中取出一个连接给用户
     * @return
     */
    public static Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public static void close(Connection conn, Statement state, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        try {
            if(state != null){
                state.close();
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        try {
            if(conn != null){
                conn.close();
            }

        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
