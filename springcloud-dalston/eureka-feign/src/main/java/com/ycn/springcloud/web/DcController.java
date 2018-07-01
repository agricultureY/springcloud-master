package com.ycn.springcloud.web;

import com.ycn.springcloud.client.DcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feign")
public class DcController {

    @Autowired
    private DcClient dcClient;

    @RequestMapping("/dc")
    public String dc() {
        return dcClient.consumer();
    }
}
