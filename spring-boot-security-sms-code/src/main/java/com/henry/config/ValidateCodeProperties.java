package com.henry.config;


import com.henry.properties.img.ImageCodeProperties;
import com.henry.properties.sms.SmsCodeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
  * @Description: 把这个两个当成配置属性
  *
  * @author henry
  * @date 2021/10/15 上午10:17
  */
@Data
@ConfigurationProperties(prefix = "com.henry.processor")
public class ValidateCodeProperties {

    private ImageCodeProperties image = new ImageCodeProperties();
    private SmsCodeProperties sms = new SmsCodeProperties();

}
