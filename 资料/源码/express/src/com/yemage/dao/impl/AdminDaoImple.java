package com.yemage.dao.impl;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.yemage.dao.BaseAdminDao;
import com.yemage.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * @author yemage
 */
public class AdminDaoImple implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = " UPDATE ADMIN SET LOGINTIME=?,LOGINIP=? WHERE USERNAME=?";
    private static final String SQL_LOGIN = "SELECT ID FROM ADMIN WHERE USERNAME=? AND PASSWORD=?";

    /**
     * 根据用户名，更新登录的时间和登录的ip
     *
     * @param username
     * @param date
     * @param ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = null;

        try {
            //2.预编译SQL语句
            ps = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            //3.添加参数
            ps.setDate(1,  new java.sql.Date(date.getTime()));
            ps.setString(2,ip);
            ps.setString(3,username);
            //4.执行
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5.释放资源
            DruidUtil.close(conn,ps,null);
        }


    }

    /**
     * 管理员根据账号和密码进行登录
     *
     * @param username
     * @param password
     * @return 登录的结果，true表示登录成功
     */
    @Override
    public boolean login(String username, String password) {
        //1.获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //2.预编译SQL语句
            ps = conn.prepareStatement(SQL_LOGIN);

            //3.添加参数
            ps.setString(1,username);
            ps.setString(2,password);
            //4.执行并返回结果
            resultSet = ps.executeQuery();
            //5.根据查询结果，返回
            return resultSet.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace(); 
        }finally {
            //6.释放资源
            DruidUtil.close(conn,ps,resultSet);
        }
        return false;
    }
}
