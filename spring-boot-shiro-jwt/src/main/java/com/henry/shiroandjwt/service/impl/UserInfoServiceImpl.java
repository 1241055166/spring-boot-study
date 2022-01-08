package com.henry.shiroandjwt.service.impl;


import com.henry.shiroandjwt.common.CommonResultStatus;
import com.henry.shiroandjwt.entity.SysUserInfo;
import com.henry.shiroandjwt.entity.dto.UserTokenDTO;
import com.henry.shiroandjwt.exception.BusinessException;
import com.henry.shiroandjwt.jwt.JwtUtil;
import com.henry.shiroandjwt.mapper.SysUserInfoMapper;
import com.henry.shiroandjwt.service.UserInfoService;
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
    public UserTokenDTO login(UserTokenDTO userInfo) {
        // 从数据库获取对应用户名密码的用户
        SysUserInfo uInfo = userInfoMapper.getUserByLogin(userInfo.getUsername());
        if (null == uInfo) {
            throw new BusinessException(CommonResultStatus.USERNAME_ERROR);
        } else if (!userInfo.getPassword().equals(uInfo.getPassword())) {
            throw new BusinessException(CommonResultStatus.PASSWORD_ERROR);
        }
        //生成jwtToken
        userInfo.setToken(JwtUtil.sign(userInfo.getUsername(),String.valueOf(System.currentTimeMillis())));
        return userInfo;
    }




}
