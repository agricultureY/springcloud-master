package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

    @Autowired
    DiscoveryClient discoveryClient;

    @RequestMapping("/dc")
    public String dc(){
        String service = "service: " + discoveryClient.getServices();
        System.out.println("<--------------"+service+"------------>");
        return service;
    }
}
