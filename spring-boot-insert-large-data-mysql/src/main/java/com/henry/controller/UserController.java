package com.henry.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.henry.common.AjaxResult;
import com.henry.domain.UserDO;
import com.henry.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: 批量插入1000万条数据
 * @Author: huangjw-b
 * @Date: 2021/12/16
 */
@RestController
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("batchInsert")
    public AjaxResult batchInsert() {
        int num = 2000;
        CountDownLatch latch = new CountDownLatch(1);
        List<UserDO> userList = new ArrayList<>();
        new Thread(() -> {
            for (int i = 0; i < 10000000; i++) {
                UserDO userDO = new UserDO();
                userDO.setName("黄先生====》" + i);
                userDO.setRemark("测试插入1000万数据");
                userList.add(userDO);
            }
            latch.countDown();
        }).start();
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
        //2000条为一批，插入1000万条
        List<List<UserDO>> partition = Lists.partition(userList, num);
        partition.stream().forEach(user -> {
            int rows = userMapper.batchInsert(user);
            System.err.println("插入数据成功，rows:" + rows);
        });
        return AjaxResult.success();
    }

    @GetMapping("all")
    public AjaxResult all(){
        return AjaxResult.success(userMapper.selectList(new LambdaQueryWrapper<>()));
    }
}