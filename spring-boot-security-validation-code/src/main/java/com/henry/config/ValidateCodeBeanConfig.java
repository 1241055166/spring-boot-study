package com.henry.config;


import com.henry.properties.ImageCodeProperties;
import com.henry.service.ValidateCodeGeneratorService;
import com.henry.service.impl.ImageCodeGeneratorServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
  * @Description: 初始化一些bean对象
  *
  * @author henry
  * @date 2021/10/14 上午10:17
  */
@Configuration
public class ValidateCodeBeanConfig {


    @Bean
    public ValidateCodeGeneratorService imageCodeGenerator() {
        ImageCodeGeneratorServiceImpl imageCodeGenerator = new ImageCodeGeneratorServiceImpl();
        imageCodeGenerator.setImageCodeProperties(imageCodeProperties());
        return imageCodeGenerator;
    }

    @Bean
    public ImageCodeProperties imageCodeProperties() {
        return new ImageCodeProperties();
    }
}
