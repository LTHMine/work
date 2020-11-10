package com.lth.work.util;

import com.lth.work.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry ){

//        注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new SessionInterceptor());
        registration.addPathPatterns("/admin/*");                      //后台都需要拦截

    }
}