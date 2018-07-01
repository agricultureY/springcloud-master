package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class EurekaZuulApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaZuulApplication.class).web(true).run(args);
    }
}
