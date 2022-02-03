package com.henry.service.impl;


import com.henry.config.ValidateCodeProperties;
import com.henry.dto.sms.SmsCode;
import com.henry.service.ValidateCodeGeneratorService;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 短信验证码生成器
 *
 * @author henry
 * @date 2021/10/13 上午11:09
 */
@Data
@Component("smsCodeGeneratorService")
public class SmsCodeGeneratorServiceImpl implements ValidateCodeGeneratorService {

    @Autowired
    private ValidateCodeProperties validateCodeProperties;


    @Override
    public SmsCode generate(ServletWebRequest request) {
        //生成随机数
        String code = RandomStringUtils.randomNumeric(validateCodeProperties.getSms().getLength());
        //这里有一个参数也是前端需要传来的 就是用户的手机号
        String mobile = null;
        try {
            mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        return new SmsCode(code, validateCodeProperties.getSms().getExpireIn(), mobile);
    }
}
