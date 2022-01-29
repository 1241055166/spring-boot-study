package com.henry.controller;

import com.henry.domain.UserDO;
import com.henry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description：
 * @Author：henry
 * @Date：2021/11/15 下午4:52
 * @Versiion：1.0
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("all")
    public Object all() {
        return userService.all();
    }

    @PostMapping("allMybatisPlus")
    public Object allMybatisPlus() {
        return userService.allMybatisPlus();
    }

    @PostMapping("add")
    public Object add(@RequestBody UserDO userDO) {
        return userService.add(userDO);
    }

    @PostMapping("update")
    public Object update(@RequestBody UserDO userDO) {
        return userService.update(userDO);
    }

    @GetMapping("delete")
    public Object delete(@RequestParam("id") String id) {
        return userService.delete(id);
    }
}
