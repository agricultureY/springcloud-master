package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/consul")
public class DcController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/health")
    public String health() {
        return "hello health ";
    }

    @RequestMapping("/dc")
    public String dc(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("consul-client");
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/dc";
        System.out.println("<------------" + url + "-------------->");
        return restTemplate.getForObject(url, String.class);
    }
}
