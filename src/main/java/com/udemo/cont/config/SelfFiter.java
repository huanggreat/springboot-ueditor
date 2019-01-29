package com.udemo.cont.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SelfFiter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String url = request.getRequestURI();
        //System.out.println(url);
        if (url.contains("/ueditor/jsp/controller")) {//ueditor的不拦截
            //System.out.println("使用自定义过滤器");
            filterChain.doFilter(servletRequest, servletResponse);
        }else{

        }
    }

    @Override
    public void destroy() {

    }
}
