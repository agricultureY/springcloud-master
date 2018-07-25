package com.ycn.springcloud.client;

import com.ycn.springcloud.client.hystrix.IntegralClientHystrix;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.client
 * @ClassName Integral
 * @Date 2018/7/3 10:42
 */
@FeignClient(value = "jdbc-lcn-integral", fallback = IntegralClientHystrix.class)
public interface IntegralClient {

    @RequestMapping("/addUser")
    ResponseEntity<ResponseWrapper> addUser(User user);

    @RequestMapping("/addUserNotTransaction")
    ResponseEntity<ResponseWrapper> addUserNotTransaction(User user);

    @RequestMapping("/getAllUser")
    ResponseEntity<ResponseWrapper> getAllUser();
}
