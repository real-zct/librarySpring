package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2023-03-07
 */
@Data
  @EqualsAndHashCode(callSuper = false)
    public class Borrow implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer uid;

    private Integer bid;

    @TableField(fill = FieldFill.INSERT)//@TableField(fill = FieldFill.INSERT)是mybatisPlus的对表的自动填充标注，要配合handler处理器使用,本次定义的是MyMetaObjectHandler类
    private LocalDateTime startTime;//fill = FieldFill.INSERT表示当出现添加操作时，进行数据填充，填充逻辑走的是handler中的insertFill方法。
    //这里填充的是当前系统时间
    //之前采用1.1.16版本的druid连接池，一直获取不到LocalDateTime类型的字段，报错，因为druid和LocalDateTime类型不兼容
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime endTime;

    private Integer status = 0;//借阅状态：0-待确认；1-审批通过；2-审批未通过；3-归还待审批


}
