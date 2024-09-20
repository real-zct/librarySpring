package com.southwind.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
@Component//记得加，用于实例化Bean
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("startTime", LocalDateTime.now(),metaObject);//将当前系统时间填充给startTime值
        this.setFieldValByName("endTime", LocalDateTime.now(),metaObject);//将当前系统时间填充给startTime值
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
