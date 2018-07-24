package com.ycn.springcloud.controller;

import com.ycn.redistool.lock.RedisLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName RedisLockController
 * @Date 2018/7/22 16:55
 */
@RestController
@RequestMapping("/redisLock")
public class RedisLockController {

    @Autowired
    private RedisLock redisLock;

    String key = "key";

    @RequestMapping("/useRedisLock")
    public String useRedisLock() {
        String request = UUID.randomUUID().toString();
        System.out.println("pre lock--"+request);
        try {
            boolean locktest = redisLock.tryNonBlockingLock(key, request);
            if (!locktest) {
                System.out.println("locked error");
                return "locked error";
            }
            //do something
            System.out.println("do something.............");
        } finally {
            System.out.println("-----------");
        }
        return "locked success-->request:"+request;
    }

    @RequestMapping("/unlock")
    public String unlock(String request) {
        if(redisLock.unlock(key,request))
            return "unlock success";
        return "unlock error";
    }
}
