package com.ycn.springcloud.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon")
public class DcController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/dc")
    public String dc(){
        return restTemplate.getForObject("http://eureka-client/dc", String.class);
    }
}
