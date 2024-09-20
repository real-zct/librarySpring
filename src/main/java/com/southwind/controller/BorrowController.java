package com.southwind.controller;

import com.southwind.entity.Borrow;
import com.southwind.entity.User;
import com.southwind.service.BorrowService;
import com.southwind.vo.BorrowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @GetMapping("/add")
    public String add(Integer bookId, HttpSession session){
        User user=(User)session.getAttribute("user");
        this.borrowService.add(user.getId(),bookId);
        return "redirect:/borrow/list";//这里会转到/borrow/list对应的Controller方法中。
        // 若这里不加redirect，这里会把/borrow/list当做视图来加载，即去找list.html
    }

    @GetMapping("/list")
    public String list(HttpSession session, Model model){
        User user=(User)session.getAttribute("user");
        List<BorrowVO> borrowVoList=this.borrowService.borrowList(user.getId());
        model.addAttribute("list",borrowVoList);
        return "/user/borrow";//跳转到/user/borrow.html
    }
}
