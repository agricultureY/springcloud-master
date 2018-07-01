package com.ycn.springcloud.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件上传配置类
 */
@Configuration
public class MultipartSupportConfig {

    @Bean
    public Encoder feignFromEncoder(){
        return new SpringFormEncoder();
    }
}
