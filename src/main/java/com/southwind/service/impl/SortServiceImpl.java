package com.southwind.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.southwind.entity.Sort;
import com.southwind.mapper.SortMapper;
import com.southwind.service.SortService;
import org.springframework.stereotype.Service;

@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements SortService {
}
