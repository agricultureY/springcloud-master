package com.ycn.springcloud.service;

/**
 * @author ycn
 * @package com.ycn.springcloud.service
 * @ClassName AccountService
 * @Date 2018/7/11 11:31
 */
public interface AccountService {

    void increaseAmount(String acctId, Double amount);

    void decreaseAmount(String acctId, Double amount);
}
