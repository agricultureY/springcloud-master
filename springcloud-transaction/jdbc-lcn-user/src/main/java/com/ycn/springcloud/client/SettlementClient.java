package com.ycn.springcloud.client;

import com.ycn.springcloud.client.hystrix.SettlementClientHystrix;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ycn
 * @package com.ycn.springcloud.client
 * @ClassName SettlementClient
 * @Date 2018/7/3 9:46
 */
@FeignClient(value = "jdbc-lcn-settlement", fallback = SettlementClientHystrix.class)
public interface SettlementClient {

    @RequestMapping("/addUser")
    ResponseEntity<ResponseWrapper> addUser(User user);

    @RequestMapping("/addUserNotTransaction")
    ResponseEntity<ResponseWrapper> addUserNotTransaction(User user);

    @RequestMapping("/getAllUser")
    ResponseEntity<ResponseWrapper> getAllUser();

    @RequestMapping("/delAllUser")
    ResponseEntity<ResponseWrapper> delAllUser();
}
