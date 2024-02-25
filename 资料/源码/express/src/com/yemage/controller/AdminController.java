package com.yemage.controller;

import com.yemage.bean.Message;
import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.service.AdminService;
import com.yemage.util.JSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author yemage
 */
public class AdminController {

    @ResponseBody("/admin/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        //1.接收参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //2.调用Service传参数，获取结果
        boolean result = AdminService.login(username, password);
        //3.根据结果，准备不同的返回数据
        Message message = null;
        if(result){
            message = new Message("登录成功",0);    //{result:"登录成功",status:0}
            //登录时间和更新ip
            Date date = new Date();
            String ip = request.getRemoteAddr();
            AdminService.updateLoginAndIp(username,date,ip);
            request.getSession().setAttribute("adminUserName","username");
        }else{
            message = new Message("登录失败",-1);    //{result:"登录失败",status:-1}
        }
        //4.将数据转换为JSON
        String json = JSONUtil.toJSON(message);
        //5.将JSON回复给Ajax
        return json;
    }
}
