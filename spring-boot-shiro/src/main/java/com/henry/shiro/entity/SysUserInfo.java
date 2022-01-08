package com.henry.shiro.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value="sys_user_info")
public class SysUserInfo implements Serializable {

    @TableId(value="id")
    private Long id;

    private String username;

    private String password;

    private Long roleId;

    private static final long serialVersionUID = 1L;

}