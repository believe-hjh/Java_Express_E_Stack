package com.yemage.mvc.test1;

import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.mvc.annotation.ResponseView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yemage
 */
public class TestController {

    @ResponseBody("/login.do")
    public String login(HttpServletRequest request, HttpServletResponse response){
        return "login success";
    }

    @ResponseView("/reg.do")
    public String reg(HttpServletRequest request, HttpServletResponse response){
        return "success.jsp";
    }

}
