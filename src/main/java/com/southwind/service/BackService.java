package com.southwind.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.southwind.entity.Back;
import com.southwind.vo.BackVO;

import java.util.List;

public interface BackService extends IService<Back> {
    public List<BackVO> backList();//筛选admin界面的归还处理页面数据
}
