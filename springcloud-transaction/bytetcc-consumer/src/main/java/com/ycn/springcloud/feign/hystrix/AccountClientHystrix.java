package com.ycn.springcloud.feign.hystrix;

import com.ycn.springcloud.feign.AccountClient;
import org.springframework.stereotype.Component;

/**
 * @author ycn
 * @package com.ycn.springcloud.feign.hystrix
 * @ClassName AccountClientHystrix
 * @Date 2018/7/11 16:49
 */
@Component
public class AccountClientHystrix implements AccountClient {
    @Override
    public void increaseAmount(String accountId, double amount) {
        System.out.println("account client increase error!");
    }

    @Override
    public void decreaseAmount(String accountId, double amount) {
        System.out.println("account client decrease error!");
    }
}
