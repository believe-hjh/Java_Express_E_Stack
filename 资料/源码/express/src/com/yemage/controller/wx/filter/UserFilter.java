package com.yemage.controller.wx.filter;

import com.yemage.bean.User;
import com.yemage.util.UserUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author yemage
 */
@WebFilter({"/index.html"})
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        User wxUser = UserUtil.getWxUser(session);
        if(wxUser != null){
            chain.doFilter(servletRequest,servletResponse);
        }else {
            ((HttpServletResponse)servletResponse).sendRedirect("login.html");
        }
    }

    @Override
    public void destroy() {

    }
}
