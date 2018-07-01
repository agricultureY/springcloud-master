package com.ycn.springcloud.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

/**
 * 配置临时文件路径
 */
//@Configuration
public class MultipartConfig {

    /**
     * 配置文件上传临时路径
     * @return
     */
//    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory multipartConfigFactory = new MultipartConfigFactory();
        String tmpPath = "E:/tmp/";
        File tmpFile = new File(tmpPath);
        if (!tmpFile.exists()) {
            tmpFile.mkdirs();
        }
        multipartConfigFactory.setLocation(tmpPath);
        return multipartConfigFactory.createMultipartConfig();
    }
}
