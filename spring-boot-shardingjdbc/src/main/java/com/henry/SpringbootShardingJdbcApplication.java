package com.henry;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//忽略自动装配DataSource
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//扫描Mapper
@MapperScan("com.henry.mapper")
//开启事务
@EnableTransactionManagement
public class SpringbootShardingJdbcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShardingJdbcApplication.class, args);
    }

}
