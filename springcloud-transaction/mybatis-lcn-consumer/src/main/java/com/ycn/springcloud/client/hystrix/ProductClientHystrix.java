package com.ycn.springcloud.client.hystrix;

import com.ycn.springcloud.client.ProductClient;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author ycn
 * @package com.ycn.springcloud.client.hystrix
 * @ClassName ProductClientHystrix
 * @Date 2018/7/4 11:35
 */
@Component
public class ProductClientHystrix implements ProductClient {
    @Override
    public ResponseEntity<ResponseWrapper> addUser(User user) {
        return ResponseEntity.ok(ResponseWrapper.markServerError());
    }

    @Override
    public ResponseEntity<ResponseWrapper> getAllUser() {
        return ResponseEntity.ok(ResponseWrapper.markServerError());
    }
}
