package com.southwind.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.southwind.entity.Admin;
import com.southwind.entity.Sysadmin;
import com.southwind.entity.User;
import com.southwind.form.LoginForm;
import com.southwind.mapper.AdminMapper;
import com.southwind.mapper.SysadminMapper;
import com.southwind.mapper.UserMapper;
import com.southwind.result.LoginResult;
import com.southwind.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service//spring帮助创建管理对象，使我们不需要关注对象之间的依赖注入，IoC帮助我们完成了
public class LoginServiceImpl implements LoginService {
    @Autowired//借助IoC动态代理来创建Usermapper对象，并将其注入进来
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private SysadminMapper sysadminMapper;
    @Override
    public LoginResult login(LoginForm loginForm) {
        /**不同的用户类型有不同的表，所以要先判断用户类型
         * 1.验证用户名是否存在
         * 2.验证密码是否正确
         *
         *
         * */
        LoginResult loginResult = new LoginResult();
        switch(loginForm.getType()){
            case 1:
                //普通用户
                QueryWrapper<User> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("username",loginForm.getUsername());//含义就是where username=传入的username值
                User user=this.userMapper.selectOne(queryWrapper);
                if(user==null){//表中没有该用户
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                if(!user.getPassword().equals(loginForm.getPassword())){//密码错误
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登陆成功");
                loginResult.setCode(0);
                loginResult.setObject(user);
                break;
            case 2:
                //管理员
                QueryWrapper<Admin> adminQueryWrapper=new QueryWrapper<>();
                adminQueryWrapper.eq("username",loginForm.getUsername());//含义就是where username=传入的username值
                Admin admin=this.adminMapper.selectOne(adminQueryWrapper);
                if(admin==null){//表中没有该用户
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                if(!admin.getPassword().equals(loginForm.getPassword())){//密码错误
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登陆成功");
                loginResult.setCode(0);
                loginResult.setObject(admin);
                break;
            case 3:
                //系统管理员
                QueryWrapper<Sysadmin> sysadminQueryWrapper=new QueryWrapper<>();
                sysadminQueryWrapper.eq("username",loginForm.getUsername());//含义就是where username=传入的username值
                Sysadmin sysadmin=this.sysadminMapper.selectOne(sysadminQueryWrapper);
                if(sysadmin==null){//表中没有该用户
                    loginResult.setMsg("用户名不存在");
                    loginResult.setCode(-1);
                    return loginResult;
                }
                if(!sysadmin.getPassword().equals(loginForm.getPassword())){//密码错误
                    loginResult.setMsg("密码错误");
                    loginResult.setCode(-2);
                    return loginResult;
                }
                loginResult.setMsg("登陆成功");
                loginResult.setCode(0);
                loginResult.setObject(sysadmin);
                break;
        }
        return loginResult;
    }
}
