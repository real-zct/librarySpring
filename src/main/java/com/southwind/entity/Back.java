package com.southwind.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
    public class Back implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer brid;

    private Integer status = 0;//默认是0，0-归还待确认；1-归还成功；2-归还失败（可能有书籍损坏的情况）


}
