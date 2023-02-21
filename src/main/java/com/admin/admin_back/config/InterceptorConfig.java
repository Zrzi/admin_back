package com.admin.admin_back.config;

import com.admin.admin_back.interceptor.SecurityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * @author 陈群矜
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public SecurityInterceptor securityInterceptor() {
        return new SecurityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(securityInterceptor()).addPathPatterns("/**");
    }

    @PostConstruct
    public void post() {
        System.out.println("InterceptorConfig起效");
    }

}
