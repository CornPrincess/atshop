package com.atshop.shiro.warreport;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan(basePackages = "com.atshop.shiro.warreport.mapper")
@SpringBootApplication
public class WarreportApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarreportApplication.class, args);
    }
}
