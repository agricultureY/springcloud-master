package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DcController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/health")
    public String health() {
        return "hello health ";
    }

    @RequestMapping("/dc")
    public String dc() {
        String services = "services: " + discoveryClient.getServices();
        System.out.println("<-----------" + services + "--------------->");
        return services;
    }
}
