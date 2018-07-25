package com.ycn.springcloud.client;

import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ycn
 * @package com.ycn.springcloud.client
 * @ClassName ProductClient
 * @Date 2018/7/4 11:30
 */
@FeignClient(value = "mybatis-lcn-product")
public interface ProductClient {

    @RequestMapping("/addUser")
    ResponseEntity<ResponseWrapper> addUser(User user);

    @RequestMapping("/getAllUser")
    ResponseEntity<ResponseWrapper> getAllUser();


}
