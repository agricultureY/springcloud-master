package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
/**
 * 基于Ribbon的客户端负载均衡的消费者
 * 
 * Ribbon是一个基于HTTP和TCP客户端的负载均衡器
 * Ribbon可以在通过客户端中配置的ribbonServerList服务端列表去轮询访问以达到均衡负载的作用
 * 当Ribbon与Eureka联合使用时，ribbonServerList会被DiscoveryEnabledNIWSServerList重写，扩展成从Eureka注册中心中获取服务端列表
 * 	同时它也会用NIWSDiscoveryPing来取代IPing，它将职责委托给Eureka来确定服务端是否已经启动
 * 
 * @Package: com.ycn.springcloud
 * @author: ycn
 * @date: 2018年5月30日 上午9:37:52
 */
@SpringBootApplication
@EnableDiscoveryClient//发现服务
@EnableCircuitBreaker//开启断路由
public class EurekaRibbonApplication {
	
	/**
	 * 创建RestTemplate实例，通过@LoadBalanced开启负载均衡功能
	 * @return
	 */
	@Bean
	@LoadBalanced
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaRibbonApplication.class, args);
	}
}
