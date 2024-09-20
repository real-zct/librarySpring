package com.southwind.controller;


import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.vo.AdminBorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
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
@RequestMapping("/admin")//@RequestMapping注解表示这个控制器处理的所有HTTP请求都是以“/sysadmin”为前缀的
//@RequestMapping 的 value 属性是通过当前请求的请求地址来匹配请求
public class AdminController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;

    @GetMapping("/{url}")
    //@GetMapping就相当于@RequestMapping(method=RequestMethod.GET),它会将get映射到特定的方法上。
    public String redirect(@PathVariable("url") String url){
        return "/admin/"+url;
    }
    //@PathVariable 映射 URL 绑定的占位符
    //通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @GetMapping("/borrowList")
    public String borrowList(Model model){
        List<AdminBorrowVO> adminBorrowVOList = this.borrowService.adminBorrowList();
        model.addAttribute("list",adminBorrowVOList);
        return "/admin/borrow";
    }
    @GetMapping("/allow")
    public String allowBorrow(Integer id){
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(1);//通过借阅申请
        this.borrowService.updateById(borrow);
        return "redirect:/admin/borrowList";
    }
    @GetMapping("/notAllow")
    public String notAllowBorrow(Integer id){
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(2);//不通过借阅申请
        this.borrowService.updateById(borrow);
        Book book = this.bookService.getById(borrow.getBid());
        book.setNumber(book.getNumber()+1);
        this.bookService.updateById(book);
        return "redirect:/admin/borrowList";
    }


}

