package com.henry;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(
		exclude = {DataSourceAutoConfiguration.class, MybatisPlusAutoConfiguration.class}
)
public class SpringBootDatasourseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDatasourseApplication.class, args);
	}

}
