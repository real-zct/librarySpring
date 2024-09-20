package com.southwind.controller;

import com.southwind.entity.Back;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.User;
import com.southwind.service.BackService;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.vo.BackVO;
import com.southwind.vo.BorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/back")
public class BackController {
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BackService backService;
    @Autowired
    private BookService bookService;

    @GetMapping("/list")
    public String backList(HttpSession session, Model model){
        User user = (User)session.getAttribute("user");
        List<BorrowVO> backVOList = this.borrowService.backList(user.getId());
        model.addAttribute("back",backVOList);
        return "/user/back";

    }

    @GetMapping("/add")
    public String add(Integer id){
        Back back = new Back();
        back.setBrid(id);
        this.backService.save(back);//插入一条数据
        Borrow borrow = this.borrowService.getById(id);
        borrow.setStatus(3);//将borrow数据设置为归还待审批状态。防止点击还书之后页面还显示该记录
        this.borrowService.updateById(borrow);//更新表中该条记录。
        return "redirect:/back/list";//跳转到backList方法
    }
    @GetMapping("adminList")
    public String adminList(Model model){
        List<BackVO> backVOList = this.backService.backList();
        model.addAttribute("list",backVOList);
        return "/admin/back";
    }
    @GetMapping("/allow")
    public String allowReturn(Integer id){
        Back back = this.backService.getById(id);
        back.setStatus(1);//设置为确认归还成功
        this.backService.updateById(back);//更新到数据库中
        Borrow borrow = this.borrowService.getById(back.getBrid());
        Book book = this.bookService.getById(borrow.getBid());
        book.setNumber(book.getNumber()+1);
        this.bookService.updateById(book);
        return "redirect:/back/adminList";
    }
}
