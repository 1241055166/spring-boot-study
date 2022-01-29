package com.henry.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.henry.domain.UserDO;
import com.henry.domain.UserMybatisDO;
import com.henry.mapper.UserMapper;
import com.henry.mapper.UserMybatisPlusMapper;
import com.henry.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * @Description：
 * @Author：henry
 * @Date：2021/11/15 下午4:53
 * @Versiion：1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserMybatisPlusMapper userMybatisPlusMapper;

    @Override
    public List<UserDO> all() {
        log.info("---info----");
        log.debug("----debug----");
        log.error("----error----");
        //mybatis 分页
        PageHelper.startPage(4, 3);
        return userMapper.all();
    }

    @Override
    public List<UserMybatisDO> allMybatisPlus() {
        //mybatis plus 分页
        int start = 4;
        int end = 3;
        IPage<UserMybatisDO> page = new Page<>(start, end);
        return userMybatisPlusMapper.selectPage(page, null).getRecords();
    }

    @Override
    //开启事务
    @Transactional
    public Integer add(UserDO userDO) {
        userDO.setId(UUID.randomUUID().toString().replace("-", ""));
        userDO.setTags(LocalDateTime.now().getSecond());
        //userMapper.add(userDO);
        //模拟事务失败
        //int num = 1 / 0;
        //userDO.setAge(99);
        //userDO.setId(UUID.randomUUID().toString().replace("-", ""));
        return userMapper.add(userDO);
    }

    @Override
    public Integer update(UserDO userDO) {
        return userMapper.update(userDO);
    }

    @Override
    public Integer delete(String id) {
        return userMapper.delete(id);
    }
}
