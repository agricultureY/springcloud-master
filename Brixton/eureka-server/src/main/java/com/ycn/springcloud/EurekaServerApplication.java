package com.ycn.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * 服务注册中心--服务发现者
 * Eureka Server除了单点运行之外，还可以通过运行多个实例，并进行互相注册的方式来实现高可用的部署，
 * 	所以我们只需要将Eureke Server配置其他可用的serviceUrl就能实现高可用部署。
 * 
 * @Package: com.ycn.springcloud
 * @author: ycn
 * @date: 2018年5月30日 上午9:43:07
 */
@EnableEurekaServer//启动服务注册中心提供给其他应用对话
@SpringBootApplication
public class EurekaServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaServerApplication.class).web(true).run(args);
	}
}
