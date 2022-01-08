package com.henry.domain;

/**
 * @Description:
 * @Author: huangjw-b
 * @Date: 2021/12/16
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 测试插入表
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("test_insert")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


}
