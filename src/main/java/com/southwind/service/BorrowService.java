package com.southwind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.entity.Borrow;
import com.southwind.vo.AdminBorrowVO;
import com.southwind.vo.BorrowVO;

import java.util.List;

public interface BorrowService extends IService<Borrow> {
    //接口extends IService<Borrow>，实现类extends ServiceImpl<BorrowMapper,Borrow>可以帮助我们默认继承一些基础的增删改查方法可供调用
    public void add(Integer uid,Integer bid);
    public List<BorrowVO> borrowList(Integer uid);
    public List<BorrowVO> backList(Integer uid);
    public List<AdminBorrowVO> adminBorrowList();//筛选admin界面的结束处理界面的数据
}
