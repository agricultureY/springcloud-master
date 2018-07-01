package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrixDashboard
@SpringCloudApplication
public class HystrixMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixMonitorApplication.class, args);
    }
}
