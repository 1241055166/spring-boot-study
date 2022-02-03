package com.henry.service.impl;


import com.henry.entity.User;
import com.henry.mapper.UserMapper;
import com.henry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author henry
 * @Description: 用户实现类
 * @date 2022/02/03 上午9:13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> list() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    public String saveOne(User user) {
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setStatus(1);
        userMapper.insert(user);
        return "保存成功";
    }
}
