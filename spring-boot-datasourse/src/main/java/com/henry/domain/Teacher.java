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
@TableName("t_teacher")
public class Teacher {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("t_name")
    private String tName;


}
