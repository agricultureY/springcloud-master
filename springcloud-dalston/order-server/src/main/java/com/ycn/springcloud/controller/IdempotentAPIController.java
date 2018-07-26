package com.ycn.springcloud.controller;

import com.ycn.springcloud.annotation.LxToken;
import com.ycn.springcloud.constant.TokenEnum;
import com.ycn.springcloud.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * 幂等性API接口测试
 *
 * @author ycn
 * @package com.ycn.springcloud.controller
 * @ClassName IdempotentAPIController
 * @Date 2018/7/26 8:55
 */
@RestController
@RequestMapping("/api")
public class IdempotentAPIController {

    @Autowired
    private RedisUtil redisUtil;
    private static final String PREFIX = "api_";

    @RequestMapping("/getToken")
    public String getToken() {
        String token = UUID.randomUUID().toString();
        boolean boo = redisUtil.set(PREFIX, token, token, 10 * 60);
        if(boo)
            return (PREFIX + token);
        return "获取失败";
    }

    @RequestMapping("/testLxToken")
    @LxToken(type = TokenEnum.TOKEN_FORM)
    public String testLxToken() {
        return "请求成功";
    }
}
