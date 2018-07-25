package com.ycn.springcloud.client.hystrix;

import com.ycn.springcloud.client.ShopcarClient;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author ycn
 * @package com.ycn.springcloud.client.hystrix
 * @ClassName ShopcarClient
 * @Date 2018/7/3 18:38
 */
@Component
public class ShopcarClientHystrix implements ShopcarClient {
    @Override
    public ResponseEntity<ResponseWrapper> addUser(User user) {
        throw new RuntimeException("Shopcar添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> addUserNotTransaction(User user) {
        throw new RuntimeException("Shopcar添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> getAllUser() {
        throw new RuntimeException("Shopcar查询失败");
    }

}
