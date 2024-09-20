package com.southwind.service;


import com.southwind.form.LoginForm;
import com.southwind.result.LoginResult;

public interface LoginService {
    public LoginResult login(LoginForm loginForm);
    //返回Object而不是具体的User或Manager是为了将用户、管理员等不同类型的登录考虑进来

}
