package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.ycn.springcloud.filter.AccessZuulFilter;

/**
 * 服务网关启动;处理跨域问题及服务统一调用
 * 服务网关是微服务架构中一个不可或缺的部分。通过服务网关统一向外系统提供REST API的过程中，
 * 	除了具备服务路由、均衡负载功能之外，它还具备了权限控制等功能。
 * 	Spring Cloud Netflix中的Zuul就担任了这样的一个角色，为微服务架构提供了前门保护的作用，
 * 	同时将权限控制这些较重的非业务逻辑内容迁移到服务路由层面，使得服务集群主体能够具备更高的可复用性和可测试性。
 * 
 * @Package: com.ycn.springcloud
 * @author: ycn
 * @date: 2018年6月4日 上午9:14:49
 */
@EnableZuulProxy//开启网关代理
@SpringCloudApplication
public class ZuulServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
	}
	
	@Bean
	public AccessZuulFilter accessZuulFilter() {
		return new AccessZuulFilter();
	}
}
