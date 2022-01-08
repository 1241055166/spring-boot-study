package com.henry.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Henry
 * @Date: 2021/10/17 下午9:25
 * @Description: 自定义注解
 * 拦截器拦截请求时，判断调用的地址对应的Controller方法是否有自定义注解，有的话说明该接口方法进行 幂等
 * 后台利用反射如果扫描到这个注解，就会处理这个方法实现自动幂等，使用元注解ElementType.METHOD表示它只能放在方法上，
 * etentionPolicy.RUNTIME表示它在运行时。
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {
}
