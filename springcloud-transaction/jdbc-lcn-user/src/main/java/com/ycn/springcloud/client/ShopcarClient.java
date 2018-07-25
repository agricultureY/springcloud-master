package com.ycn.springcloud.client;

import com.ycn.springcloud.client.hystrix.ShopcarClientHystrix;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ycn
 * @package com.ycn.springcloud.client
 * @ClassName ShopcarClient
 * @Date 2018/7/3 18:38
 */
@FeignClient(value = "jdbc-lcn-shopcar", fallback = ShopcarClientHystrix.class)
public interface ShopcarClient {

    @RequestMapping("/addUser")
    ResponseEntity<ResponseWrapper> addUser(User user);

    @RequestMapping("/addUserNotTransaction")
    ResponseEntity<ResponseWrapper> addUserNotTransaction(User user);

    @RequestMapping("/getAllUser")
    ResponseEntity<ResponseWrapper> getAllUser();
}
