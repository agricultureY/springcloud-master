package com.ycn.springcloud.client.hystrix;

import com.ycn.springcloud.client.SettlementClient;
import com.ycn.springcloud.entity.ResponseWrapper;
import com.ycn.springcloud.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ycn
 * @package com.ycn.springcloud.client.hystrix
 * @ClassName SettlementClientHystrix
 * @Date 2018/7/3 9:55
 */
@Component
public class SettlementClientHystrix implements SettlementClient {
    @Override
    public ResponseEntity<ResponseWrapper> addUser(User user) {
        throw new RuntimeException("settlement添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> addUserNotTransaction(User user) {
        throw new RuntimeException("settlement添加失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> getAllUser() {
        throw new RuntimeException("settlement查询user失败");
    }

    @Override
    public ResponseEntity<ResponseWrapper> delAllUser() {
        throw new RuntimeException("settlement删除失败");
    }
}
