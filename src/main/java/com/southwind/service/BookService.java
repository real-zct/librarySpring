package com.southwind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.entity.Book;
import com.southwind.vo.PageVO;

public interface BookService extends IService<Book> {
    //接口extends IService<Book>，实现类extends ServiceImpl<BookMapper,Book>可以帮助我们默认继承一些基础的增删改查方法可供调用
    public PageVO pageList(Integer currentPage);

    public PageVO searchByKeyword(String keyword,Integer currentPage);

    public PageVO searchBySort(Integer sortId,Integer currentPage);
}
