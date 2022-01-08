package com.henry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.henry.mapper"})
public class SpringBootInsertLargeDataMysqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootInsertLargeDataMysqlApplication.class, args);
	}

}
