package com.ycn.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

@EnableZipkinStreamServer//使用stream方式启动zipkin server
@SpringBootApplication
public class ZipkinStreamServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZipkinStreamServerApplication.class, args);
    }
}
