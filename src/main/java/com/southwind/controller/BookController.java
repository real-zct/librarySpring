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
import com.southwind.vo.BookVO;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private SortService sortService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BackService backService;

    @GetMapping("/list/{current}")//{current}表示接收一个参数，并用current命名
    public String list(@PathVariable("current") Integer current, Model model){
        //@PathVariable将url的current参数命名为Integer的current参数传入到方法中
        /*QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.gt("number",0);//gt表示greater，对应sql语句，where number > 0
        List<Book> list=this.bookService.list(queryWrapper);
        List<BookVO> result=new ArrayList<>();
        for(Book book:list){
            BookVO bookVO=new BookVO();
            BeanUtils.copyProperties(book,bookVO);//将book的属性值复制给bookVO
            //org.springframework.beans.BeanUtils下的copyProperties方法可以用于目标对象（target/dest）中不包含被copy的对象（source/orig）
            QueryWrapper<Sort> sortQueryWrapper=new QueryWrapper<>();
            sortQueryWrapper.eq("id",book.getSid());
            Sort sort=this.sortService.getOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        model.addAttribute("list",result);//将list存储到request中返回给html页面进行接收*/
        //因为上述方法逻辑太长了，这里有planB，将其改到service中，并增加分页功能。
        PageVO pageVO = this.bookService.pageList(current);
        model.addAttribute("page",pageVO);
        model.addAttribute("sortList",this.sortService.list());
        return "/user/list";//之后显示在/user/list.html中
    }
    @PostMapping("/search")
    public String searchByKeyword(String keyWord,Integer current, Integer sid,Model model){
        PageVO pageVO=null;
        if(!sid.equals(0)){
            //按书籍类别检索
            pageVO=this.bookService.searchBySort(sid ,current);
        }else{
            pageVO = this.bookService.searchByKeyword(keyWord, current);
        }

        model.addAttribute("page",pageVO);
        model.addAttribute("sortList",this.sortService.list());
        return "user/list";
    }
    @PostMapping("/findByKey")
    public String findByKey(String key,Model model){
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(key),"name",key)
                .or()
                .like(StringUtils.isNotBlank(key),"author",key)
                .or()
                .like(StringUtils.isNotBlank(key),"publish",key);
        List<Book> bookList = this.bookService.list(queryWrapper);
        List<BookVO> bookVOList=new ArrayList<>();
        for(Book book:bookList){
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book,bookVO);
            Sort sort = this.sortService.getById(book.getSid());
            bookVO.setSname(sort.getName());
            bookVOList.add(bookVO);
        }
        model.addAttribute("list",bookVOList);
        return "/sysadmin/book";
    }
    @PostMapping("/add")
    private String addBook(Book book){
        this.bookService.save(book);
        return "redirect:/sysadmin/bookList";
    }
    @GetMapping("/findById/{id}")
    private String fingById(@PathVariable("id") Integer id,Model model){
        Book book = this.bookService.getById(id);
        model.addAttribute("book",book);
        model.addAttribute("list",this.sortService.list());
        return "/sysadmin/updateBook";
    }
    @PostMapping("/update")
    private String updateBook(Book book){
        this.bookService.updateById(book);
        return "redirect:/sysadmin/bookList";
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Integer id){
        //这里不能单独删除书的记录，应该级联删除借阅这本书的记录和归还这本书的记录，因为外键关系
        QueryWrapper<Borrow> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("bid",id);
        List<Borrow> borrowList = this.borrowService.list(queryWrapper);
        for(Borrow borrow:borrowList){
            QueryWrapper<Back> backQueryWrapper=new QueryWrapper<>();//先删除back表相关数据，再删除borrow表
            backQueryWrapper.eq("brid",borrow.getId());
            this.backService.remove(backQueryWrapper);
            this.borrowService.removeById(borrow.getId());
        }
        this.bookService.removeById(id);
        return "redirect:/sysadmin/bookList";
    }

}
