package com.yemage.dao;

/**
 * @author yemage
 */

import java.util.Date;

/**
 * 用于定义express_admin表格的操作规范
 */
public interface BaseAdminDao {

    /**
     * 根据用户名，更新登录的时间和登录的ip
     * @param username
     * @param date
     * @param ip
     */
    void updateLoginTime(String username, Date date,String ip);


    /**
     * 管理员根据账号和密码进行登录
     * @param username
     * @param password
     * @return 登录的结果，true表示登录成功
     */
    boolean login(String username,String password);
}
