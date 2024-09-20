package com.southwind.form;

import lombok.Data;

import javax.annotation.sql.DataSourceDefinition;
@Data//直接帮助创建getter，setter方法
public class LoginForm {//Form文件夹下的文件用来接收或传递表单信息的
    private String username;
    private String password;
    private Integer type;


}
