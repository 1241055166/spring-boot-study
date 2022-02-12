package com.henry.service;


import com.henry.pojo.Result;
import com.henry.pojo.User;

public interface UserService {

    /**
     * 增
     */
    Result insert(User user);

    /**
     * 删
     */
    Result delete(User user);

    /**
     * 改
     */
    Result update(User user);

    /**
     * 查
     */
    Result select(User user);
}
