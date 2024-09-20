package com.southwind.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.southwind.entity.Back;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Sort;
import com.southwind.service.BackService;
import com.southwind.service.BookService;
import com.southwind.service.BorrowService;
import com.southwind.service.SortService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sort")
public class SortController {
    @Autowired
    private SortService sortService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BackService backService;


    @GetMapping("/list")
    public String sortList(Model model){
        model.addAttribute("list",this.sortService.list());
        return "/sysadmin/addBook";
    }
    @PostMapping("/search")
    public String searchSort(String name,Model model){
        QueryWrapper<Sort> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);
        List<Sort> sortList = this.sortService.list(queryWrapper);
        model.addAttribute("list",sortList);
        return "/sysadmin/sort";
    }
    @PostMapping("/add")
    private String addBook(Sort sort){
        this.sortService.save(sort);
        return "redirect:/sysadmin/sortList";
    }
    @GetMapping("/findById/{id}")
    private String fingById(@PathVariable("id") Integer id, Model model){
        Sort sort = this.sortService.getById(id);
        model.addAttribute("sort",sort);
        return "/sysadmin/updateSort";
    }
    @PostMapping("/update")
    private String updateSort(Sort sort){
        this.sortService.updateById(sort);
        return "redirect:/sysadmin/sortList";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id){//级联删除，根据外键
        QueryWrapper<Book> bookQueryWrapper=new QueryWrapper<>();
        bookQueryWrapper.eq("sid",id);
        List<Book> bookList = this.bookService.list(bookQueryWrapper);
        for(Book book:bookList){
            QueryWrapper<Borrow> borrowQueryWrapper=new QueryWrapper<>();
            borrowQueryWrapper.eq("bid",book.getId());
            List<Borrow> borrowList = this.borrowService.list(borrowQueryWrapper);
            for(Borrow borrow:borrowList){
                QueryWrapper<Back> backQueryWrapper=new QueryWrapper<>();
                borrowQueryWrapper.eq("brid",borrow.getId());
                this.backService.remove(backQueryWrapper);
                this.borrowService.removeById(borrow.getId());
            }
            this.bookService.removeById(book.getId());
        }

        this.sortService.removeById(id);
        return "redirect:/sysadmin/sortList";
    }
}
