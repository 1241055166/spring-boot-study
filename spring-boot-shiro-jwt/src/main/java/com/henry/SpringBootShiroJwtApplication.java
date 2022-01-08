package com.henry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.henry.shiroandjwt.mapper"})
public class SpringBootShiroJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootShiroJwtApplication.class, args);
    }

}
