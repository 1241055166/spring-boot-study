package com.henry.shiroandjwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.henry.shiroandjwt.entity.SysUserInfo;

public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {
    //通过用户名获取用户信息
    SysUserInfo getUserByLogin(String username);

}