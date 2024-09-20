package com.southwind.controller;

import com.southwind.entity.Admin;
import com.southwind.entity.Sysadmin;
import com.southwind.entity.User;
import com.southwind.form.LoginForm;
import com.southwind.result.LoginResult;
import com.southwind.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")//跟login.html中登录action的值相对应
    public String login(LoginForm loginForm, Model model, HttpSession session){
        //LoginForm用来接收页面上的username、password、用户类型等参数
        LoginResult result = loginService.login(loginForm);
        /*if(result.getMsg().equals("用户名不存在")){
            尽量不要出现文字匹配的情况
        }*/
        String url="";
        if(result.getCode().equals(-1)||result.getCode().equals(-2)){
            url="login";
            model.addAttribute("msg",result.getMsg());//结果会显示在login.html的<p th:text="${msg}"></p>组件中
            /*
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.setViewName("login");
            modelAndView.addObject("msg",result.getMsg());
            这个和上面可以实现同样的功能，唯一不同的是，modelAndView最终会直接走视图解析器
            thymeleaf:
            prefix: classpath:/templates/
            suffix: .html
            去找页面classpath:/templates/下的login.html文件，
            而url不同，如果url="login"，过程会跟modelAndView一样；

            但如果url="redirect:/user/index"
            当Spring Boot接收到一个HTTP GET请求时，它会查找具有相应URL路径的控制器方法
            他就会先去找UserController.java中的redirect方法，重定向url
            再结合application.xml配置文件中定义的
            thymeleaf:
            prefix: classpath:/templates/
            suffix: .html，
            结合前后缀，在返回实际地址classpath:/templates/user/index.html
            */
        }else if(result.getCode().equals(0)){
            switch (loginForm.getType()){
                case 1:
                    session.setAttribute("user",(User)result.getObject());
                    url="redirect:/user/index";
                    break;
                case 2:
                    session.setAttribute("admin",(Admin)result.getObject());
                    url="redirect:/admin/index";
                    break;
                case 3:
                    session.setAttribute("sysadmin",(Sysadmin)result.getObject());
                    url="redirect:/sysadmin/index";
                    break;
            }
        }
        return url;

    }
}
