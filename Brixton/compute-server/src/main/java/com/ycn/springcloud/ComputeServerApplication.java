package com.ycn.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 服务提供者
 * 
 * @Package: com.ycn.springcloud
 * @author: ycn
 * @date: 2018年5月30日 上午9:42:24
 */
@SpringBootApplication
@EnableDiscoveryClient//开启Eureka中的DiscoveryClient实现，才能实现Controller中对服务信息的输出
public class ComputeServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ComputeServerApplication.class).web(true).run(args);
	}
}
