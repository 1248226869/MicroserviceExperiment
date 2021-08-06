package com.cxytiandi.sharding.config.filter;


import javax.servlet.*;
import java.io.IOException;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/3
 * @Version 1.0.0
 */
public class MyOtherFilter implements Filter {

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)throws IOException, ServletException {
        long start = System.currentTimeMillis();
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("Ohter Execute cost="+(System.currentTimeMillis()-start));
    }

    @Override
    public void destroy() {

    }
}
