package com.yemage.mvc;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yemage
 */
public class DispatchServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        String path = config.getInitParameter("contentConfigLocation");
        InputStream is = DispatchServlet.class.getClassLoader().getResourceAsStream(path);
        HandlerMapping.load(is);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //1.获取用户请求的uri  /xx.do
        String uri = req.getRequestURI();
        HandlerMapping.MVCMapping mapping = HandlerMapping.get(uri);
        if(mapping == null){
            resp.sendError(404,"MVC:映射地址不存在" + uri);
            return;
        }
        Object obj = mapping.getObj();
        //用于请求的方法
        Method method = mapping.getMethod();
        Object result = null;
        try {
            result = method.invoke(obj, req, resp);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        switch (mapping.getType()){
            case TEXT:
                resp.getWriter().write((String) result);
                break;
            case VIEW:
                resp.sendRedirect((String) result);
                break;
        }
    }
}
