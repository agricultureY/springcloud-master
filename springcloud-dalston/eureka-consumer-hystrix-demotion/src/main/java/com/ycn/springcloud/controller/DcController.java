package com.ycn.springcloud.controller;

import com.ycn.springcloud.sevice.DcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName DcController
 * @Date 2018/6/20 16:48
 */
@RestController
@RequestMapping("/hystrix/demotion")
public class DcController {

    @Autowired
    DcService dcService;

    @RequestMapping("/dc")
    public String dc() {
        return dcService.consumer();
    }
}
