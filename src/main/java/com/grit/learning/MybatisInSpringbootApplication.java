package com.grit.learning;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * 在启动类中添加对 Mapper 包扫描@MapperScan，Spring Boot 启动的时候会自动加载包路径下的 Mapper
 * 或者直接在 Mapper 类上面添加注解@Mapper，建议使用上面那种，不然每个 Mapper 加个注解会很麻烦。
 */

@SpringBootApplication
@MapperScan("com.grit.learning.mapper")
public class MybatisInSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybatisInSpringbootApplication.class, args);
	}
}
