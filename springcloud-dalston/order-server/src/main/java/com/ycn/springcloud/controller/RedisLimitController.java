package com.ycn.springcloud.controller;

import com.ycn.redistool.annotation.CommonLimit;
import com.ycn.redistool.annotation.ControllerLimit;
import com.ycn.redistool.limit.RedisLimit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName RedisLimitController
 * @Date 2018/7/24 14:16
 */
@RestController
@RequestMapping("/redisLimit")
public class RedisLimitController {

    @Autowired
    private RedisLimit redisLimit;

    @RequestMapping("/testRedisLimit")
    public String testRedisLimit() {
        if(redisLimit.limit())
            return "do something..............";
        return "redis limit...........";
    }

    @RequestMapping("/testControllerLimit")
    @CommonLimit
    public String testControllerLimit() {
        return "do something..............";
    }
}
