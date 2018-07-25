package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class JdbcLcnShopcarApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcLcnShopcarApplication.class, args);
    }
}
