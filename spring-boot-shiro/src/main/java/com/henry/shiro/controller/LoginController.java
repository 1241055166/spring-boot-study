package com.henry.shiro.controller;


import com.henry.shiro.common.AjaxResult;
import com.henry.shiro.entity.SysUserInfo;
import com.henry.shiro.service.UserInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: LoginController
 * @Description: TODO
 * @Date: 2020/11/8
 */
@RestController
@RequestMapping("adminLogin")
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("login")
    public AjaxResult login(@RequestBody SysUserInfo userInfo){
        // 拿到主体
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getUsername(),userInfo.getPassword());
        subject.login(token);
        Map<String,Object> info=new HashMap<>();
        info.put("msg","登录成功");
        info.put("session_id",subject.getSession().getId());
        return AjaxResult.success(info);
    }
}
