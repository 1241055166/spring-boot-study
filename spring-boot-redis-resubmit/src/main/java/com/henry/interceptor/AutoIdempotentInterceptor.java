package com.henry.interceptor;

import com.henry.annotion.AutoIdempotent;
import com.henry.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:43
 * @Description: 拦截处理器
 * 主要的功能是拦截扫描到AutoIdempotent到注解到方法,
 * 然后调用tokenService的checkToken()方法校验token是否正确，如果捕捉到异常就将异常信息渲染成json返回给前端。
 */
@Component
public class AutoIdempotentInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        if(!(handler instanceof HandlerMethod))
            return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        //拿到方法上面的自定义注解
        AutoIdempotent annotation = method.getAnnotation(AutoIdempotent.class);

        //如果不等于null说明该方法要进行幂等
        if(null != annotation){
            return tokenService.checkToken(request);
        }

        return true;
    }
}

