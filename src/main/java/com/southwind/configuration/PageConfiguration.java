package com.southwind.configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PageConfiguration {//定义一个分页拦截器，因为Mybatis Plus的分页是通过拦截器实现的
    //SpringBoot IoC注入拦截器对象即可
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
