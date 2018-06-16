package com.ycn.springcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConfigClientMsgApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigClientMsgApplication.class).web(true).run(args);
	}
}
