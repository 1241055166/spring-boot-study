package com.henry.controller;

import com.henry.annotion.AutoIdempotent;
import com.henry.service.TestService;
import com.henry.service.TokenService;
import com.henry.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:50
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/Idempotence")
public class TestController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TestService testService;

    @GetMapping("/getToken")
    public R getToken(){
        String token = tokenService.createToken();
        return R.ok().data("token",token);
    }

    //相当于添加数据接口（测试时 连续点击添加数据按钮  看结果是否是添加一条数据还是多条数据）
    @AutoIdempotent
    @PostMapping("/test/addData")
    public R addData(){
        String s = testService.testMethod();
        return R.ok().data("data",s);
    }

}
