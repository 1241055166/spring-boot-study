package com.henry.shiroandjwt.controller;


import com.henry.shiroandjwt.common.AjaxResult;
import com.henry.shiroandjwt.entity.dto.UserTokenDTO;
import com.henry.shiroandjwt.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public AjaxResult login(@RequestBody UserTokenDTO userTokenDTO){
        return AjaxResult.success(userInfoService.login(userTokenDTO));
    }
}
