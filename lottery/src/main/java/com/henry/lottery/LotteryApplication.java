package com.henry.lottery;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.henry.lottery.dal.mapper")
public class LotteryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LotteryApplication.class, args);
    }

}
