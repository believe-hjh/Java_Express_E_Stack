package com.yemage.service;

import com.yemage.dao.BaseAdminDao;
import com.yemage.dao.impl.AdminDaoImple;

import java.util.Date;

/**
 * @author yemage
 */
public class AdminService {

    private static BaseAdminDao dao = new AdminDaoImple();

    /**
     * 更新登录时间和ip
     * @param username
     * @param date
     * @param ip
     */
    public static void updateLoginAndIp(String username, Date date, String ip){
            dao.updateLoginTime(username,date,ip);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return  true表示登录成功，false表示登录失败
     */
    public static boolean login(String username,String password){
        return dao.login(username,password);
    }
}
