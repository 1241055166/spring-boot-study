package com.henry.shiroandjwt.service;


import com.henry.shiroandjwt.entity.dto.UserTokenDTO;

/**
 * @ClassName: UserInfoService
 * @Description: 用户service
 * @Date: 2020/9/25
 */
public interface UserInfoService {

    //用户登陆添加token信息
    UserTokenDTO login(UserTokenDTO userTokenDTO);

}
