package com.henry.knife4j.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


/**
 * @Description: MyBatis配置类
 * @Date: 2020/12/20
 */
@Configuration
@MapperScan("com.henry.knife4j.mbg.mapper")
public class MyBatisConfig {
}
