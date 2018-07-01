package com.ycn.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class ConsulConsumerApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ConsulConsumerApplication.class).web(true).run(args);
    }

    /**
     * 初始化restTemplate
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
