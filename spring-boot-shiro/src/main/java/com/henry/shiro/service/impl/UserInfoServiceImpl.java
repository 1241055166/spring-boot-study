package com.henry.shiro.service.impl;


import com.henry.shiro.entity.SysUserInfo;
import com.henry.shiro.mapper.SysUserInfoMapper;
import com.henry.shiro.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName: UserInfoServiceImpl
 * @Description: TODO
 * @Date: 2020/9/25
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private SysUserInfoMapper userInfoMapper;

    @Override
    public SysUserInfo getUserByLogin(String username) {
        return userInfoMapper.getUserByLogin(username);
    }
}
