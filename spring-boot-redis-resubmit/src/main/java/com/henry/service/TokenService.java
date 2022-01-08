package com.henry.service;

import com.henry.exectionhandler.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:26
 * @Description: 创建和检验
 * token服务接口：我们新建一个接口，创建token服务，里面主要是两个方法，一个用来创建token，
 * 一个用来验证token。创建token主要产生的是一个字符串，检验token的话主要是传达request对象，为什么要传request对象呢？
 * 主要作用就是获取header里面的token,然后检验，通过抛出的Exception来获取具体的报错信息返回给前端。
 */
@Service
public class TokenService {

    @Autowired
    RedisService redisService;

    //创建token
    public String createToken() {
        //使用UUID代表token
        UUID uuid = UUID.randomUUID();
        String token = uuid.toString();
        //存入redis
        boolean b = redisService.setEx(token, token, 10000L);
        return token;
    }

    //检验请求头或者请求参数中是否有token
    public boolean checkToken(HttpServletRequest request) {

        String token = request.getHeader("token");

        //如果header中是空的
        if(StringUtils.isEmpty(token)){
            //从request中拿
            token = request.getParameter("token");
            if(StringUtils.isEmpty(token)){
                throw new BaseException(20001, "缺少参数token");
            }
        }
        //如果从header中拿到的token不正确
        if(!redisService.exists(token)){
            throw new BaseException(20001, "不能重复提交-------token不正确、空");
        }
        //token正确 移除token
        if(!redisService.remove(token)){
            throw new BaseException(20001, "token移除失败");
        }
        return true;
    }
}

