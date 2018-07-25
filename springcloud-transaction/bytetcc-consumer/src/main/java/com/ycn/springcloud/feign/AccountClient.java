package com.ycn.springcloud.feign;

import com.ycn.springcloud.feign.hystrix.AccountClientHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ycn
 * @package com.ycn.springcloud.feign
 * @ClassName AccountClient
 * @Date 2018/7/11 16:48
 */
@FeignClient(value = "bytetcc-provider", fallback = AccountClientHystrix.class)
public interface AccountClient {

    @RequestMapping(method = RequestMethod.POST, value = "/increase")
    void increaseAmount(@RequestParam("acctId") String accountId, @RequestParam("amount") double amount);

    @RequestMapping(method = RequestMethod.POST, value = "/decrease")
    void decreaseAmount(@RequestParam("acctId") String accountId, @RequestParam("amount") double amount);
}
