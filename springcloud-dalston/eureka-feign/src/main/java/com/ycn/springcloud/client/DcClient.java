package com.ycn.springcloud.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "eureka-client")
public interface DcClient {

    @RequestMapping("/dc")
    String consumer();
}
