package com.cxytiandi.sharding.config.interceptors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2020/8/3
 * @Version 1.0.0
 */
@Configuration
public class MyWebMvcConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这里可以添加多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
//        registry.addInterceptor(new MyInterceptors()).addPathPatterns("/**");
    }
}
