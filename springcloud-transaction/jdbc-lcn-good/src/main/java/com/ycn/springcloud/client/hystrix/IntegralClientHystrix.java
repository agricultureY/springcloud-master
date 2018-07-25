package com.ycn.springcloud.client.hystrix;

import com.ycn.springcloud.client.IntegralClient;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author ycn
 * @package com.ycn.springcloud.client.hystrix
 * @ClassName IntegralClientHystrix
 * @Date 2018/7/3 10:43
 */
@Component
public class IntegralClientHystrix implements IntegralClient {
    @Override
    public ResponseEntity<ResponseWrapper> addUser(User user) {
        throw new RuntimeException("integral添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> addUserNotTransaction(User user) {
        throw new RuntimeException("integral添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> getAllUser() {
        throw new RuntimeException("integral查询失败");
    }

}
