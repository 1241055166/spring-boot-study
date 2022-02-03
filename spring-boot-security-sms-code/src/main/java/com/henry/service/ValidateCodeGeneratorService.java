package com.henry.service;


import com.henry.dto.ValidateCode;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * @Description: 生成验证码接口
 *
 * @author henry
 * @date 2021/10/13 上午9:46
 */
public interface ValidateCodeGeneratorService {

    /**
     * 这个常量也是用来 type+CodeGeneratorService获取对于bean对象
     */
    String CODE_GENERATOR = "CodeGeneratorService";

    /**
     * 生成验证码
     * 具体是图片验证码 还是短信验证码就需要对应的实现类
     */
    ValidateCode generate(ServletWebRequest request);
}
