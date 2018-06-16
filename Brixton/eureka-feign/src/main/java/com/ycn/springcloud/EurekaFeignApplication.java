package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
/**
 * Feign是一个声明式的Web Service客户端，它使得编写Web Serivce客户端变得更加简单
 * 	只需要使用Feign来创建一个接口并用注解来配置它既可完成
 * 
 * @Package: com.ycn.springcloud
 * @author: ycn
 * @date: 2018年5月30日 上午10:22:51
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients//开启feign功能
@EnableCircuitBreaker
public class EurekaFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaFeignApplication.class, args);
	}
}
