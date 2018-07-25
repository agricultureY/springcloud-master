package com.ycn.springcloud.client.hystrix;

import com.ycn.springcloud.client.GoodClient;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author ycn
 * @package com.ycn.springcloud.client.hystrix
 * @ClassName GoodClientHystrix
 * @Date 2018/7/3 18:35
 */
@Component
public class GoodClientHystrix implements GoodClient {
    @Override
    public ResponseEntity<ResponseWrapper> addUser(User user) {
        throw new RuntimeException("good添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> addUserNotTransaction(User user) {
        throw new RuntimeException("good添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> getAllUser() {
        throw new RuntimeException("good查询失败");
    }

}
