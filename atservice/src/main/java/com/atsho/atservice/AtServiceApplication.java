package com.atsho.atservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableTransactionManagement
//@ComponentScan("com.at.ssm.demo")
//@MapperScan("com.at.ssm.demo.dao")
public class AtServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtServiceApplication.class, args);
	}
}
