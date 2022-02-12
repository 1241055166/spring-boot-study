package com.henry.controller;


import com.henry.pojo.Result;
import com.henry.pojo.User;
import com.henry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //测试：http://localhost:8080/user/insert?username=张三疯&password=111&created=2022-02-01 11:11:11
    @RequestMapping("/insert")
    public Result insert(User user) {
        return  userService.insert(user);
    }

    //测试：http://localhost:8080/user/delete?id=44
    @RequestMapping("/delete")
    public Result delete(User user) {
        return  userService.delete(user);
    }

    //测试：http://localhost:8080/user/update?id=44&username=张四疯
    @RequestMapping("/update")
    public Result update(User user) {
        return  userService.update(user);
    }

    //测试：http://localhost:8080/user/select?id=44
    @RequestMapping("/select")
    public Result select(User user) {
        return  userService.select(user);
    }
}
