package com.ycn.springcloud;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;


@ImportResource({ "classpath:bytetcc-supports-springcloud.xml" })
@EnableDiscoveryClient
@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = "com.ycn.springcloud")
public class BytetccProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(BytetccProviderApplication.class).bannerMode(Banner.Mode.OFF).web(true).run(args);
        System.out.println("springcloud-bytetcc-provider started!");
    }
}
