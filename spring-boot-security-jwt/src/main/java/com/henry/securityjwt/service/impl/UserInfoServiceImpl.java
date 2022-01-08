package com.henry.securityjwt.service.impl;


import com.henry.securityjwt.entity.model.UserPojo;
import com.henry.securityjwt.mapper.SysUserInfoMapper;
import com.henry.securityjwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserPojo user = userInfoMapper.queryByUserName(username);
        return user;
    }
}
