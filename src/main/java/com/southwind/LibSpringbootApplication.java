package com.southwind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.southwind.mapper")
//mybatisplus的用法在引导类上添加@MapperScan注解，其属性为所要扫描的Dao/mapper所在包，
//或者在单独的dao/mapper上添加@Mapper标记要扫描的Mapper
public class LibSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibSpringbootApplication.class, args);
    }

}
