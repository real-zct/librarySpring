package com.southwind.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.entity.Book;
import com.southwind.entity.Borrow;
import com.southwind.entity.Sort;
import com.southwind.entity.User;
import com.southwind.mapper.BookMapper;
import com.southwind.mapper.BorrowMapper;
import com.southwind.mapper.SortMapper;
import com.southwind.mapper.UserMapper;
import com.southwind.service.BorrowService;
import com.southwind.vo.AdminBorrowVO;
import com.southwind.vo.BorrowVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper,Borrow> implements BorrowService {
    //接口extends IService<Borrow>，实现类extends ServiceImpl<BorrowMapper,Borrow>可以帮助我们默认继承一些基础的增删改查方法可供调用
    @Autowired
    private BorrowMapper borrowMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SortMapper sortMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void add(Integer uid, Integer bid) {
        Borrow borrow=new Borrow();
        borrow.setBid(bid);
        borrow.setUid(uid);
        this.borrowMapper.insert(borrow);
        Book book = this.bookMapper.selectById(bid);
        book.setNumber(book.getNumber()-1);
        this.bookMapper.updateById(book);

    }

    @Override
    public List<BorrowVO> borrowList(Integer uid) {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("uid",uid);
        List<Borrow> borrowList=this.borrowMapper.selectList(borrowQueryWrapper);
        List<BorrowVO> borrowVOList=new ArrayList<>();
        for (Borrow borrow: borrowList) {
            BorrowVO borrowVO=new BorrowVO();
            BeanUtils.copyProperties(borrow,borrowVO);//赋值startTime、status
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book,borrowVO);//赋值edition、publish、author
            borrowVO.setBookName(book.getName());//因为BorrowVO和Book的书名属性名不同，所以copyProperties不能赋值，需要手动赋值
            Sort sort = this.sortMapper.selectById(book.getSid());
            borrowVO.setSortName(sort.getName());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<BorrowVO> backList(Integer uid) {
        QueryWrapper<Borrow> backQueryWrapper = new QueryWrapper<>();
        backQueryWrapper.eq("uid",uid);
        backQueryWrapper.eq("status",1);
        List<Borrow> borrowList=this.borrowMapper.selectList(backQueryWrapper);
        List<BorrowVO> borrowVOList=new ArrayList<>();
        for (Borrow borrow: borrowList) {
            BorrowVO borrowVO=new BorrowVO();
            BeanUtils.copyProperties(borrow,borrowVO);//赋值startTime、status
            Book book = this.bookMapper.selectById(borrow.getBid());
            BeanUtils.copyProperties(book,borrowVO);//赋值edition、publish、author
            borrowVO.setBookName(book.getName());//因为BorrowVO和Book的书名属性名不同，所以copyProperties不能赋值，需要手动赋值
            borrowVO.setId(borrow.getId());
            borrowVOList.add(borrowVO);
        }
        return borrowVOList;
    }

    @Override
    public List<AdminBorrowVO> adminBorrowList() {
        QueryWrapper<Borrow> borrowQueryWrapper = new QueryWrapper<>();
        borrowQueryWrapper.eq("status",0);
        List<Borrow> borrows = this.borrowMapper.selectList(borrowQueryWrapper);
        List<AdminBorrowVO> adminBorrowVOList=new ArrayList<>();
        for(Borrow borrow:borrows){
            AdminBorrowVO adminBorrowVO = new AdminBorrowVO();
            BeanUtils.copyProperties(borrow,adminBorrowVO);//赋值id、startTime、status
            User user = this.userMapper.selectById(borrow.getUid());
            adminBorrowVO.setUserName(user.getUsername());
            Book book = this.bookMapper.selectById(borrow.getBid());
            adminBorrowVO.setBookName(book.getName());
            BeanUtils.copyProperties(book,adminBorrowVO);//赋值了id、author、publish、edition
            adminBorrowVO.setId(borrow.getId());//将id修正为借书记录id
            Sort sort = this.sortMapper.selectById(book.getSid());
            adminBorrowVO.setSortName(sort.getName());
            adminBorrowVOList.add(adminBorrowVO);
        }
        return adminBorrowVOList;
    }
}
