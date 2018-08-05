package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 消息恢复系统,消息状态确认系统
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling//开启任务调度
@SpringBootApplication
public class MessageCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCheckerApplication.class, args);
    }
}
