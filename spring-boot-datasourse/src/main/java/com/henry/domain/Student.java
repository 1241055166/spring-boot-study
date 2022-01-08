package com.henry.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Description:
 * @Author: huangjw-b
 */
@Data
@TableName("s_student")
public class Student {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("s_name")
    private String sName;


}
