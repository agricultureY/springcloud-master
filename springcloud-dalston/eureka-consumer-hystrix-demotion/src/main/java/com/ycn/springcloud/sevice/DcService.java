package com.ycn.springcloud.sevice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author ycn
 * @package com.ycn.springcloud.sevice
 * @ClassName DcService
 * @Date 2018/6/20 16:49
 */
@Service
public class DcService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallback")
    public String consumer() {
        return restTemplate.getForObject("http://eureka-client/dc", String.class);
    }

    public String fallback() {
        return "服务异常，降级处理............";
    }
}
