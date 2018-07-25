package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class JdbcLcnUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcLcnUserApplication.class, args);
    }
}
