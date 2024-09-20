package com.southwind.mapper;

import com.southwind.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
public interface UserMapper extends BaseMapper<User> {
    //继承mybatisplus的BaseMapper之后，mybatisplus就会作为动态代理，帮助创建所需要的应用类并放入框架中。

}
