package com.ycn.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@EnableFeignClients//开启feign调用
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaFeignApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaFeignApplication.class).web(true).run(args);
    }
}
