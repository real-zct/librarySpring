package com.southwind.vo;

import lombok.Data;

@Data
public class BookVO {//vo代表viewObject，此类用于将user和sort做联合时对应视图view的类
    private Integer id;

    private String name;

    private String sname;//类别名

    private Integer number;

    private String author;

    private String publish;

    private String edition;

}
