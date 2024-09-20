package com.southwind.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.southwind.entity.Admin;
import com.southwind.entity.Book;
import com.southwind.entity.Sort;
import com.southwind.entity.User;
import com.southwind.service.BookService;
import com.southwind.service.SortService;
import com.southwind.service.UserService;
import com.southwind.vo.BookVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
@Controller
@RequestMapping("/sysadmin")//@RequestMapping注解表示这个控制器处理的所有HTTP请求都是以“/sysadmin”为前缀的
//@RequestMapping 的 value 属性是通过当前请求的请求地址来匹配请求，默认参数就是value的值
public class SysadminController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;
    @Autowired
    private SortService sortService;

    @GetMapping("/{url}")//@GetMapping注解可以用于类和方法上，用于定义HTTP GET请求的URL路径。
    //当Spring Boot接收到一个HTTP GET请求时，它会查找具有相应URL路径的控制器方法，然后调用该方法处理请求。
    //@GetMapping就相当于@RequestMapping(method=RequestMethod.GET),它会将get映射到特定的方法上。
    public String redirect(@PathVariable("url") String url){
        return "/sysadmin/"+url;
    }
    //@PathVariable 映射 URL 绑定的占位符
    //通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/userList")
    public String userList(Model model){
        List<User> userList = this.userService.list();
        model.addAttribute("list",userList);
        return "/sysadmin/user";
    }
    @PostMapping("/findByName")//表单采用post请求，就要在后台采用@PostMapping接收
    public String findByName(String username,Model model){//接收参数名到对应表单中的name名
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),"username",username);
        List<User> list=this.userService.list(queryWrapper);
        model.addAttribute("list",list);
        return "/sysadmin/user";

    }
    @GetMapping("/bookList")
    public String bookList(Model model){
        List<Book> books = this.bookService.list();
        List<BookVO> bookVOList=new ArrayList<>();
        for(Book book:books){
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book,bookVO);
            Sort sort = this.sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOList.add(bookVO);
        }
        model.addAttribute("list",bookVOList);
        return "/sysadmin/book";
    }
    @GetMapping("/sortList")
    public String sortList(Model model){
        List<Sort> userList = this.sortService.list();
        model.addAttribute("list",userList);
        return "/sysadmin/sort";
    }
}

