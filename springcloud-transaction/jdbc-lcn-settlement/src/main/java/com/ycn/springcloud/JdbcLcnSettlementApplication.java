package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author ycn
 * @package com.ycn.springcloud
 * @ClassName JdbcLcnSettlementApplication
 * @Date 2018/7/2 17:57
 */
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class JdbcLcnSettlementApplication {

    public static void main(String[] args) {
        SpringApplication.run(JdbcLcnSettlementApplication.class, args);
    }
}
