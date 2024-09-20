package com.southwind.controller;


import com.southwind.entity.User;
import com.southwind.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
@Controller
@RequestMapping("/user")//@RequestMapping注解表示这个控制器处理的所有HTTP请求都是以“/user”为前缀的
//@RequestMapping 的 value 属性是通过当前请求的请求地址来匹配请求
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/{url}")//@GetMapping注解可以用于类和方法上，用于定义HTTP GET请求的URL路径。
    //当Spring Boot接收到一个HTTP GET请求时，它会查找具有相应URL路径的控制器方法，然后调用该方法处理请求。
    //@GetMapping就相当于@RequestMapping(method=RequestMethod.GET),它会将get映射到特定的方法上。
    public String redirect(@PathVariable("url") String url){
        return "/user/"+url;
    }
    //@PathVariable 映射 URL 绑定的占位符
    //通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();//销毁session就相当于退出了，最后返回登录页面
        return "login";
    }
    @PostMapping("/add")
    public String addUser(User user){//springMVC提供的功能，传入的参数和实体数据类的属性名对应，就可以直接封装成对象传入
        this.userService.save(user);
        return "redirect:/sysadmin/userList";
    }
    @GetMapping("/findById/{id}")
    //对应前端：<a type="button" class="btn btn-success" th:href="'/user/findById/'+${user.id}">修改</a>
    //可以采用上述方式接收参数，并定义@PathVariable("id")获取并将参数传入方法中
    public String findById(@PathVariable("id") Integer id, Model model){
        User user = this.userService.getById(id);
        model.addAttribute("user",user);
        return "/sysadmin/updateUser";
    }

    @PostMapping("/update")
    public String updateUser(User user){
        this.userService.updateById(user);
        return "redirect:/sysadmin/userList";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){
        this.userService.removeById(id);
        return "redirect:/sysadmin/userList";
    }

}

