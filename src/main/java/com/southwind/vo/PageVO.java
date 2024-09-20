package com.southwind.vo;

import lombok.Data;

import java.util.List;
@Data
public class PageVO {
    private long currentNo;//long是因为对应的page对象要求的current和size字段是long形
    private long totalNo;
    private List data;
}
