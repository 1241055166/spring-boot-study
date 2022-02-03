package com.henry.service;


import com.henry.entity.User;

import java.util.List;

/**
 * @author henry
 * @Description: 用户相关接口
 * @date 2022/02/03 下午6:32
 */
public interface UserService {

    /**
     * 获取所有用户信息
     */
    List<User> list();

    /**
     * 单个 保存用户信息
     *
     * @param user
     */
    String saveOne(User user);

}