package com.yemage.mvc.test2;

import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.mvc.annotation.ResponseView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yemage
 */
public class XXXController {

    @ResponseBody("/test1.do")
    public String test1(HttpServletRequest request, HttpServletResponse response){
        return "hahaha";
    }

    @ResponseBody("/test2")
    public String test2(HttpServletRequest request, HttpServletResponse response){
        return "heiheihei";
    }

    @ResponseView("/test3.do")
    public String test3(HttpServletRequest request, HttpServletResponse response){
        return "xxx.html";
    }


}
