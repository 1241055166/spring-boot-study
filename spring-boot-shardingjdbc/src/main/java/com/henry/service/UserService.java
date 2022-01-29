package com.henry.service;

import com.henry.domain.UserDO;
import com.henry.domain.UserMybatisDO;

import java.util.List;

/**
 * @Description：
 * @Author：henry
 * @Date：2021/11/15 下午4:52
 * @Versiion：1.0
 */
public interface UserService {
    //mybatis 查询
    List<UserDO> all();

    //mybatisplus 查询
    List<UserMybatisDO> allMybatisPlus();

    //添加
    Integer add(UserDO userDO);

    //更新
    Integer update(UserDO userDO);

    //删除
    Integer delete(String id);
}
