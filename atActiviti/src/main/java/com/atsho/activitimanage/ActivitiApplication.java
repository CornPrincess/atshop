package com.atsho.activitimanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@EnableEurekaClient
@RestController
public class ActivitiApplication {
    @GetMapping("/service")
    public String service(){
        return "service";
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
}