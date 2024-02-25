package com.yemage.util;

import com.yemage.bean.User;

import javax.servlet.http.HttpSession;

/**
 * @author yemage
 */
public class UserUtil {

    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }

    public static String getUserPhone(HttpSession session){
        //TODO:还没有编写柜子端，未存储任何的录入人信息
        return "18888888888";
    }

    public static String getLoginSMS(HttpSession session,String userPhone){

         return (String) session.getAttribute(userPhone);
    }
    public static void setLoginSMS(HttpSession session,String userPhone,String code){

        session.setAttribute(userPhone,code);
    }


    public static void setWxUser(HttpSession session, User user){
        session.setAttribute("wxUser",user);
    }

    public static User getWxUser(HttpSession session){
        return (User) session.getAttribute("wxUser");
    }
}
