package com.southwind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedirectController {

    @GetMapping("/{url}")
    //导航栏输入http://localhost:8080/login，这里读取导航栏的login并将其返回，
    // 之后结合application.xml配置文件中定义的
    // thymeleaf:
    //    prefix: classpath:/templates/
    //    suffix: .html，
    //结合前后缀，在返回实际地址classpath:/templates/login.html
    public String redirect(@PathVariable("url") String url){
        return url;
    }

    @GetMapping("favicon.ico")
    @ResponseBody
    void returnNoFavicon() {
    }

}
