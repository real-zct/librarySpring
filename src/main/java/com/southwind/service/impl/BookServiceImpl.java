package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.entity.Book;
import com.southwind.entity.Sort;
import com.southwind.entity.User;
import com.southwind.mapper.BookMapper;
import com.southwind.mapper.SortMapper;
import com.southwind.mapper.UserMapper;
import com.southwind.service.BookService;
import com.southwind.vo.BookVO;
import com.southwind.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    //接口extends IService<Book>，实现类extends ServiceImpl<BookMapper,Book>可以帮助我们默认继承一些基础的增删改查方法可供调用
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private SortMapper sortMapper;
    @Override
    public PageVO pageList(Integer currentPage) {
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.gt("number",0);//gt表示greater，对应sql语句，where number > 0
        Page<Book> page=new Page<>(currentPage,10);//当前页号为currentPage，一页显示5条数据
        Page<Book> resultPage=this.bookMapper.selectPage(page,queryWrapper);//selectPage结果按页返回
        PageVO pageVO=new PageVO();
        pageVO.setCurrentNo(resultPage.getCurrent());
        pageVO.setTotalNo(resultPage.getPages());//getPages获取共多少条数据
        List<BookVO> result=new ArrayList<>();
        for(Book book:resultPage.getRecords()){//getRecord获取page对象的记录
            BookVO bookVO=new BookVO();
            BeanUtils.copyProperties(book,bookVO);//将book的属性值复制给bookVO
            //org.springframework.beans.BeanUtils下的copyProperties方法可以用于目标对象（target/dest）中不包含被copy的对象（source/orig）
            QueryWrapper<Sort> sortQueryWrapper=new QueryWrapper<>();
            sortQueryWrapper.eq("id",book.getSid());
            Sort sort=this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        // 注意此时分页功能还未为实现，因为Mybatis Plus的分页是通过拦截器实现的，
        // 这里还需要定义一个PageConfiguration为springboot注入分页拦截器
        return pageVO;
    }

    @Override
    public PageVO searchByKeyword(String keyword, Integer currentPage) {
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.gt("number",0);//gt表示greater，对应sql语句，where number > 0
        queryWrapper.like(StringUtils.isNotBlank(keyword),"name",keyword)
                .or()
                .like(StringUtils.isNotBlank(keyword),"publish",keyword)
                .or()
                .like(StringUtils.isNotBlank(keyword),"author",keyword);
        //like()表示模糊查询，第一个参数为布尔值，若布尔值为true才会在queryWrapper中加入该模糊查询语句
        //StringUtils.isNotBlank()为mybatisPlus中方法，用来判断String类型是否为空。
        //本语句的含义是若keyword不为空，那就做模糊匹配name或author或publish字段。
        Page<Book> page=new Page<>(currentPage,10);//当前页号为currentPage，一页显示5条数据
        Page<Book> resultPage=this.bookMapper.selectPage(page,queryWrapper);//selectPage结果按页返回
        PageVO pageVO=new PageVO();
        pageVO.setCurrentNo(resultPage.getCurrent());
        pageVO.setTotalNo(resultPage.getPages());//getPages获取共多少条数据
        List<BookVO> result=new ArrayList<>();
        for(Book book:resultPage.getRecords()){//getRecord获取page对象的记录
            BookVO bookVO=new BookVO();
            BeanUtils.copyProperties(book,bookVO);//将book的属性值复制给bookVO
            //org.springframework.beans.BeanUtils下的copyProperties方法可以用于目标对象（target/dest）中不包含被copy的对象（source/orig）
            QueryWrapper<Sort> sortQueryWrapper=new QueryWrapper<>();
            sortQueryWrapper.eq("id",book.getSid());
            Sort sort=this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        // 注意此时分页功能还未为实现，因为Mybatis Plus的分页是通过拦截器实现的，
        // 这里还需要定义一个PageConfiguration为springboot注入分页拦截器
        return pageVO;
    }

    @Override
    public PageVO searchBySort(Integer sortId, Integer currentPage) {
        QueryWrapper<Book> queryWrapper=new QueryWrapper<>();
        queryWrapper.gt("number",0);//gt表示greater，对应sql语句，where number > 0
        queryWrapper.eq("sid",sortId);
        Page<Book> page=new Page<>(currentPage,10);//当前页号为currentPage，一页显示5条数据
        Page<Book> resultPage=this.bookMapper.selectPage(page,queryWrapper);//selectPage结果按页返回
        PageVO pageVO=new PageVO();
        pageVO.setCurrentNo(resultPage.getCurrent());
        pageVO.setTotalNo(resultPage.getPages());//getPages获取共多少条数据
        List<BookVO> result=new ArrayList<>();
        for(Book book:resultPage.getRecords()){//getRecord获取page对象的记录
            BookVO bookVO=new BookVO();
            BeanUtils.copyProperties(book,bookVO);//将book的属性值复制给bookVO
            //org.springframework.beans.BeanUtils下的copyProperties方法可以用于目标对象（target/dest）中不包含被copy的对象（source/orig）
            QueryWrapper<Sort> sortQueryWrapper=new QueryWrapper<>();
            sortQueryWrapper.eq("id",book.getSid());
            Sort sort=this.sortMapper.selectOne(sortQueryWrapper);
            bookVO.setSname(sort.getName());
            result.add(bookVO);
        }
        pageVO.setData(result);
        // 注意此时分页功能还未为实现，因为Mybatis Plus的分页是通过拦截器实现的，
        // 这里还需要定义一个PageConfiguration为springboot注入分页拦截器
        return pageVO;
    }
}
