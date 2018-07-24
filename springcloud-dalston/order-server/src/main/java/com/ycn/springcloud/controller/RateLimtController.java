package com.ycn.springcloud.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.ycn.springcloud.annotation.LxRateLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * guava  rateLimit限流应用
 *
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName RateLimtController
 * @Date 2018/7/22 23:29
 */
@RestController
@RequestMapping("/rateLimit")
public class RateLimtController {

    //每秒只发出1个令牌
    private RateLimiter rateLimiter = RateLimiter.create(1.0);

    @RequestMapping("/testRateLimit")
    public String testRateLimit() {
        long start = System.currentTimeMillis();
        //500ms内如果没有没有获得令牌返回false
        if(rateLimiter.tryAcquire(500, TimeUnit.MILLISECONDS))
            return "get token success --> wait time:"+ (System.currentTimeMillis() - start) + "ms";
        return "get token failure";
    }

    @RequestMapping("/testAnnotation")
    @LxRateLimit(perSecond = 1.0, timeOut = 500)
    public String testAnnotation() {
        return "get token success";
    }
}
