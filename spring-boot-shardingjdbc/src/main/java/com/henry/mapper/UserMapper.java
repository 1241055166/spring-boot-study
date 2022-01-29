package com.henry.mapper;

import com.henry.domain.UserDO;

import java.util.List;

/**
 * @Author: Henry
 * @Date: 2021/11/15 下午10:58
 * @Description:
 */
public interface UserMapper {

    //查询
    List<UserDO> all();

    //添加
    Integer add(UserDO userDO);

    //更新
    Integer update(UserDO userDO);

    //删除
    Integer delete(String id);
}
