package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/eureka")
public class DcController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/dc")
    public String dc(){
        /**
         * 实现原理：
         *      1、通过loadBalancerClient的choose函数来负载均衡的选出一个eureka-client的服务实例，这个服务实例的基本信息存储在ServiceInstance中
         *      2、通过ServiceInstance对象中的信息拼接访问路径
         *      3、RestTemplate实现对服务提供者接口的调用
         */
        ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/dc";
        System.out.println("<------------" + url + "-------------->");
        return restTemplate.getForObject(url, String.class);
    }
}
