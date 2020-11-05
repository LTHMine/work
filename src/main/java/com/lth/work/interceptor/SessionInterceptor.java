package com.lth.work.interceptor;

import com.lth.work.pojo.Student;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
        throws Exception {
    }
    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
        throws Exception {
    }
    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        //登录放行
        if ("/admin/toLogin".equals(arg0.getRequestURI()) ||
            "/admin/login".equals(arg0.getRequestURI())) {  //不拦截这个两个请求
            return true;}
        //重定向
        String stu = (String) arg0.getSession().getAttribute("users");
        if (null == stu) {
            arg1.sendRedirect("/admin/login");
            return false;}
        return true;
    }
}