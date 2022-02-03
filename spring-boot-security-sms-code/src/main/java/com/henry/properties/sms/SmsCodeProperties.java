package com.henry.properties.sms;

import com.henry.properties.CodeProperties;
import lombok.Data;

/**
 * @Description: 短信验证码属性
 *
 * @author henry
 * @date 2021/10/17 下午1:43
 */
@Data
public class SmsCodeProperties extends CodeProperties {

    public SmsCodeProperties() {
        setUrl("/mobie");
    }

}
