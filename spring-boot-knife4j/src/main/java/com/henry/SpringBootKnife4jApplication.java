package com.henry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.henry.knife4j.mbg.mapper"})
public class SpringBootKnife4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKnife4jApplication.class, args);
	}

}
