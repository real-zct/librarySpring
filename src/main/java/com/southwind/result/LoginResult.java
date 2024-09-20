package com.southwind.result;

import lombok.Data;

@Data
public class LoginResult {
    private Object object;
    private String msg;//记录查询之后的结果
    private Integer code;//状态码，记录当前表单信息是否正确，-1表示用户名不存在，-2表示密码错误，0表示正确
}
