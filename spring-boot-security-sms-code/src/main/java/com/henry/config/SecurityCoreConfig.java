package com.henry.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
  * @Description: 把ValidateCodeProperties变成配置类
  *
  * @author henry
  * @date 2021/10/13 下午1:54
  */
@Configuration
@EnableConfigurationProperties(ValidateCodeProperties.class)
public class SecurityCoreConfig {


}
