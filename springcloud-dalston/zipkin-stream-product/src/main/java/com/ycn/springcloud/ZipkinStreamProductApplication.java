package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ZipkinStreamProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinStreamProductApplication.class, args);
    }
}
